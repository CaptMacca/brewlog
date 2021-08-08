package brews.app.presentation.rest.controllers;

import brews.app.presentation.dto.user.UpdatePasswordRequest;
import brews.app.presentation.dto.user.UpdateUserDto;
import brews.app.presentation.dto.user.UserDetailsDto;
import brews.domain.User;
import brews.domain.mapper.UserMapper;
import brews.services.UserService;
import brews.util.security.jwt.request.SignUpForm;
import brews.util.security.jwt.response.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * The user api
 */
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name="User", description = "API for managing user details")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    @ResponseBody
    @Operation(description = "Get current user details", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<UserDetailsDto> getCurrentUserDetails() {
        return ResponseEntity.ok(userMapper.toUserDetailsResponse(userService.getCurrentUserDetails()));
    }

    @PutMapping("/{username}")
    @ResponseBody
    @Operation(description = "Updates a user details", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable String username, @RequestBody UpdateUserDto updateUserDto) {
        User user = userMapper.toUser(updateUserDto);
        return ResponseEntity.ok(userMapper.toUserDetailsResponse(userService.updateUserDetails(username, user)));
    }

    @PostMapping("/password")
    @ResponseBody
    @Operation(description = "Updates a users password", security = @SecurityRequirement(name="bearerAuth"))
    public ResponseEntity<Boolean> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return ResponseEntity.ok(userService.updatePassword(updatePasswordRequest.getCurrentPassword(), updatePasswordRequest.getNewPassword()));
    }

    @PostMapping("/signup")
    @ResponseBody
    @Operation(description = "Registers a new user account")
    public ResponseEntity<?> registerUser(@RequestBody SignUpForm signUpRequest) {

        User user = new User(
          signUpRequest.getFirstName(),
          signUpRequest.getSurname(),
          signUpRequest.getUsername(),
          signUpRequest.getEmail(),
          signUpRequest.getPassword());

        Set<String> roles = signUpRequest.getRoles();

        this.userService.registerUser(user, roles);

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }



}
