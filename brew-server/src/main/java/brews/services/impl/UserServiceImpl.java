package brews.services.impl;

import brews.domain.User;
import brews.domain.dto.UpdatePasswordDto;
import brews.domain.dto.UserDto;
import brews.exceptions.UserEntityNotFoundException;
import brews.exceptions.UserNotAuthenticatedException;
import brews.exceptions.UserPasswordMismatchException;
import brews.mapper.domain.UserMapper;
import brews.repository.UserRepository;
import brews.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto getCurrentUserDetails() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw new UserNotAuthenticatedException();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = getUser(userDetails.getUsername());
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto updateUserDetails(String username, UserDto userDto){
        User existingUser = getUser(username);
        userMapper.updateFromUserDto(userDto, existingUser);
        User savedUser = userRepository.save(existingUser);
        return userMapper.toUserDto(savedUser);
    }

    @Override
    public UpdatePasswordDto updatePassword(UpdatePasswordDto updatePasswordDto) {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw new UserNotAuthenticatedException();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentPassword = userDetails.getPassword();
        String oldPassword = updatePasswordDto.getCurrentPassword();
        if (passwordEncoder.matches(oldPassword, currentPassword)) {
            String newPassword = passwordEncoder.encode(updatePasswordDto.getNewPassword());
            User existingUser = getUser(userDetails.getUsername());
            existingUser.setPassword(newPassword);
            userRepository.save(existingUser);
        } else {
            throw new UserPasswordMismatchException("The supplied current password does not match the actual current password of the user");
        }
        return updatePasswordDto;
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private User getUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserEntityNotFoundException("The user could not be found"));;
        return user;
    }
}
