package brews.app.presentation.rest.controllers;

import brews.app.presentation.dto.user.UserDetailsDto;
import brews.domain.User;
import brews.app.presentation.dto.user.UpdatePasswordRequest;
import brews.app.presentation.dto.user.UpdateUserDto;
import brews.domain.mapper.UserMapper;
import brews.util.security.jwt.request.SignUpForm;
import brews.util.security.jwt.response.ResponseMessage;
import brews.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
@Api("API for managing user details")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping()
    @ResponseBody
    @ApiOperation(value="Get current user details", authorizations = { @Authorization(value = "jwtToken")})
    public ResponseEntity<UserDetailsDto> getCurrentUserDetails() {
        return ResponseEntity.ok(userMapper.toUserDetailsResponse(userService.getCurrentUserDetails()));
    }

    @PutMapping("/{username}")
    @ResponseBody
    @ApiOperation(value="Updates a user details", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable String username, @RequestBody UpdateUserDto updateUserDto) {
        User user = userMapper.toUser(updateUserDto);
        return ResponseEntity.ok(userMapper.toUserDetailsResponse(userService.updateUserDetails(username, user)));
    }

    @PostMapping("/password")
    @ResponseBody
    @ApiOperation(value="Updates a users password", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<Boolean> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return ResponseEntity.ok(userService.updatePassword(updatePasswordRequest.getCurrentPassword(), updatePasswordRequest.getNewPassword()));
    }

    @PostMapping("/signup")
    @ResponseBody
    @ApiOperation("Registers a new user account")
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
