package brews.services.impl;

import brews.domain.Role;
import brews.domain.RoleName;
import brews.domain.User;
import brews.repository.RoleRepository;
import brews.repository.UserRepository;
import brews.services.UserService;
import brews.services.exceptions.*;
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

    private static final String ADMIN_ROLE_NAME = RoleName.ROLE_ADMIN.name();
    private static final String USER_ROLE_NAME = RoleName.ROLE_USER.name();


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
    public User updateUserDetails(String username, User user) {
        User existingUser = getUser(username);
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setSurname(user.getSurname());
        return userRepository.saveAndFlush(existingUser);
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

        setRoles(user,roles);
        return userRepository.saveAndFlush(user);
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private User getUser(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserEntityNotFoundException("The user could not be found"));
    }

    private void setRoles(User user, Set<String> roles) {
        Set<Role> savedRoles = new HashSet<>();

        if (roles != null) {
            roles.forEach(role -> {
                if (ADMIN_ROLE_NAME.equals(role)) {
                    Role adminRole = roleRepository.findByRoleName(RoleName.ROLE_ADMIN)
                      .orElseThrow(() -> new RoleDoesntExistException("Fail! -> Cause: Admin Role could not be found."));
                    savedRoles.add(adminRole);
                } else if (USER_ROLE_NAME.equals(role)) {
                    Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER)
                      .orElseThrow(() -> new RoleDoesntExistException("Fail! -> Cause: User Role could not be found."));
                    savedRoles.add(userRole);
                }
            });

            user.setRoles(savedRoles);
        }
    }
}
