package br.com.personal.keystore.cross.mappers;

import br.com.personal.keystore.app.models.request.UserSaveRequest;
import br.com.personal.keystore.app.models.request.UserUpdateRequest;
import br.com.personal.keystore.app.models.response.UserResponse;
import br.com.personal.keystore.infra.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    List<UserResponse> usersToUsersResponse(List<User> user);

    User userSaveRequestToUser(UserSaveRequest userSaveRequest);

    User userUpdateRequestToUser(@MappingTarget User user, UserUpdateRequest userUpdateRequest);

}
