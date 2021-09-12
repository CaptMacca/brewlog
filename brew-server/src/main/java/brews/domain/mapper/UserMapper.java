package brews.domain.mapper;

import brews.app.presentation.dto.user.UpdateUserDto;
import brews.app.presentation.dto.user.UserDetailsDto;
import brews.domain.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDetailsDto toUserDetailsResponse(User user);

    List<UserDetailsDto> toUserDetailsResponse(List<User> users);

    public abstract void updateFromUpdateUserRequest(UpdateUserDto updateUserDto, @MappingTarget User user);

    @InheritInverseConfiguration
    User toUser(UpdateUserDto updateUserDto);

    @InheritInverseConfiguration
    List<User> toUsers(List<UpdateUserDto> updateUserDtos);
}
