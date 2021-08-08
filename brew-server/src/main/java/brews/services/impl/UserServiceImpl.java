package brews.services.impl;

import brews.domain.Role;
import brews.domain.RoleName;
import brews.domain.User;
import brews.domain.exceptions.*;
import brews.infrastructure.data.jpa.repository.RoleRepository;
import brews.infrastructure.data.jpa.repository.UserRepository;
import brews.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUserDetails() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw new UserNotAuthenticatedException();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return getUser(userDetails.getUsername());
    }

    @Override
    public User updateUserDetails(String username, User user){
        User existingUser = getUser(username);
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setSurname(user.getSurname());
        return userRepository.save(existingUser);
    }

    @Override
    public boolean updatePassword(String oldPassword, String updatePassword) {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw new UserNotAuthenticatedException();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentPassword = userDetails.getPassword();
        if (passwordEncoder.matches(oldPassword, currentPassword)) {
            String newPassword = passwordEncoder.encode(updatePassword);
            User existingUser = getUser(userDetails.getUsername());
            existingUser.setPassword(newPassword);
            userRepository.save(existingUser);
        } else {
            throw new UserPasswordMismatchException("The supplied current password does not match the actual current password of the user");
        }
        return true;
    }

    @Override
    public User registerUser(User user, Set<String> roles) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyRegisteredException("Fail -> Username is already taken!");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyRegisteredException("Fail -> Email is already in use!");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Set<Role> savedRoles = new HashSet<>();
        if (roles != null) {
            roles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(RoleName.ROLE_ADMIN)
                          .orElseThrow(() -> new RoleDoesntExistException("Fail! -> Cause: Admin Role could not be found."));
                        savedRoles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                          .orElseThrow(() -> new RoleDoesntExistException("Fail! -> Cause: User Role could not be found."));
                        savedRoles.add(userRole);
                }
            });

            user.setRoles(savedRoles);
        }
        return userRepository.save(user);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private User getUser(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserEntityNotFoundException("The user could not be found"));
    }
}
