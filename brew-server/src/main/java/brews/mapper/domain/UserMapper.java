package brews.mapper.domain;

import brews.domain.User;
import brews.domain.dto.UserDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toUserDto(User user);

    List<UserDto> toUserDtos(List<User> users);

    public abstract void updateFromUserDto(UserDto userDto, @MappingTarget User user);

    @InheritInverseConfiguration
    User toUser(UserDto userDto);

    @InheritInverseConfiguration
    List<User> toUsers(List<UserDto> userDtos);
}
