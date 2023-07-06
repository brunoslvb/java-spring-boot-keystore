package br.com.personal.keystore.cross.mappers;

import br.com.personal.keystore.app.models.request.FolderSaveRequest;
import br.com.personal.keystore.app.models.request.FolderUpdateRequest;
import br.com.personal.keystore.app.models.response.FolderResponse;
import br.com.personal.keystore.infra.entities.Folder;
import br.com.personal.keystore.infra.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FolderMapper {

    FolderResponse folderToFolderResponse(Folder folder);

    List<FolderResponse> folderToFolderResponse(List<Folder> folders);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "passwords", ignore = true)
    @Mapping(source = "folderSaveRequest.name", target = "name")
    Folder folderSaveRequestToFolder(FolderSaveRequest folderSaveRequest, User user);

    Folder folderUpdateRequestToFolder(@MappingTarget Folder folder, FolderUpdateRequest folderUpdateRequest);


}
