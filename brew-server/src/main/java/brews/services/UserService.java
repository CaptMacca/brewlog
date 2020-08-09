package brews.services;

import brews.domain.dto.UpdatePasswordDto;
import brews.domain.dto.UserDto;

public interface UserService {

    UserDto getCurrentUserDetails();
    UserDto updateUserDetails(String username, UserDto userDto);
    UpdatePasswordDto updatePassword(UpdatePasswordDto updatePasswordDto);
}
