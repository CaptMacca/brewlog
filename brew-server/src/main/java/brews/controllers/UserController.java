package brews.controllers;

import brews.domain.User;
import brews.domain.dto.UpdatePasswordRequest;
import brews.domain.dto.UpdateUserRequest;
import brews.domain.dto.UserDetailsResponse;
import brews.mapper.domain.UserMapper;
import brews.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@Api("API for managing user details")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService; this.userMapper = userMapper;
    }

    @GetMapping()
    @ApiOperation(value="Get current user details", authorizations = { @Authorization(value = "jwtToken")})
    public ResponseEntity<UserDetailsResponse> getCurrentUserDetails() {
        return ResponseEntity.ok(userMapper.toUserDetailsResponse(userService.getCurrentUserDetails()));
    }

    @PutMapping()
    @ApiOperation(value="Updates a user details", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<UserDetailsResponse> updateUser(@PathVariable String username, @RequestBody UpdateUserRequest updateUserRequest) {
        User user = userMapper.toUser(updateUserRequest);
        return ResponseEntity.ok(userMapper.toUserDetailsResponse(userService.updateUserDetails(username, user)));
    }

    @PostMapping("/password")
    @ApiOperation(value="Updates a users password", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<Boolean> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
        return ResponseEntity.ok(userService.updatePassword(updatePasswordRequest.getCurrentPassword(), updatePasswordRequest.getNewPassword()));
    }
}
