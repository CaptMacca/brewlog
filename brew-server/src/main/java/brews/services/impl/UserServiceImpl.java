package brews.services.impl;

import brews.domain.User;
import brews.exceptions.UserEntityNotFoundException;
import brews.exceptions.UserNotAuthenticatedException;
import brews.exceptions.UserPasswordMismatchException;
import brews.repository.UserRepository;
import brews.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getCurrentUserDetails() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw new UserNotAuthenticatedException();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = getUser(userDetails.getUsername());
        return user;
    }

    @Override
    public User updateUserDetails(String username, User user){
        User existingUser = getUser(username);
        BeanUtils.copyProperties(user, existingUser);
        User savedUser = userRepository.save(existingUser);
        return savedUser;
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

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private User getUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserEntityNotFoundException("The user could not be found"));;
        return user;
    }
}
