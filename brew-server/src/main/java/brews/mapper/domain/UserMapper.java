package brews.mapper.domain;

import brews.domain.User;
import brews.domain.dto.UpdateUserRequest;
import brews.domain.dto.UserDetailsResponse;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDetailsResponse toUserDetailsResponse(User user);

    List<UserDetailsResponse> toUserDetailsResponse(List<User> users);

    public abstract void updateFromUpdateUserRequest(UpdateUserRequest updateUserRequest, @MappingTarget User user);

    @InheritInverseConfiguration
    User toUser(UpdateUserRequest updateUserRequest);

    @InheritInverseConfiguration
    List<User> toUsers(List<UpdateUserRequest> updateUserRequests);
}
