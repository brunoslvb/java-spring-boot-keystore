package br.com.personal.keystore.cross.mappers;

import br.com.personal.keystore.app.models.request.PasswordSaveRequest;
import br.com.personal.keystore.app.models.request.PasswordUpdateRequest;
import br.com.personal.keystore.app.models.response.PasswordResponse;
import br.com.personal.keystore.infra.entities.Folder;
import br.com.personal.keystore.infra.entities.Password;
import br.com.personal.keystore.infra.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PasswordMapper {

    @Mapping(source = "password.folder.name", target = "folderName")
    PasswordResponse passwordToPasswordResponse(Password password);

    @Mapping(source = "passwords.folder.name", target = "folderName")
    List<PasswordResponse> passwordToPasswordResponse(List<Password> passwords);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "passwordSaveRequest.password", target = "password")
    @Mapping(source = "folder", target = "folder")
    Password passwordSaveRequestToPassword(PasswordSaveRequest passwordSaveRequest, User user, Folder folder);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "passwordUpdateRequest.password", target = "password")
    Password passwordUpdateRequestToPassword(@MappingTarget Password password, PasswordUpdateRequest passwordUpdateRequest, Folder folder);

}
