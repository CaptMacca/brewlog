package brews.controllers;

import brews.domain.dto.UpdatePasswordDto;
import brews.domain.dto.UserDto;
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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @ApiOperation(value="Get current user details", authorizations = { @Authorization(value = "jwtToken")})
    public ResponseEntity<UserDto> getCurrentUserDetails() {
        return ResponseEntity.ok(userService.getCurrentUserDetails());
    }

    @PutMapping()
    @ApiOperation(value="Updates a user details", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<UserDto> updateUser(@PathVariable String username, @RequestBody UserDto updateUserDto) {
        return ResponseEntity.ok(userService.updateUserDetails(username, updateUserDto));
    }

    @PostMapping("/password")
    @ApiOperation(value="Updates a users password", authorizations = { @Authorization(value="jwtToken")})
    public ResponseEntity<UpdatePasswordDto> updatePassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return ResponseEntity.ok(userService.updatePassword(updatePasswordDto));
    }
}
