package br.com.personal.keystore.domain.usecases;

import br.com.personal.keystore.app.models.request.FolderSaveRequest;
import br.com.personal.keystore.app.models.request.FolderUpdateRequest;
import br.com.personal.keystore.cross.mappers.FolderMapper;
import br.com.personal.keystore.infra.entities.Folder;
import br.com.personal.keystore.infra.entities.User;
import br.com.personal.keystore.infra.repositories.FolderRepository;
import br.com.personal.keystore.infra.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class FolderMaintenanceUseCase {

    FolderRepository folderRepository;

    FolderMapper folderMapper;

    UserRepository userRepository;

    public FolderMaintenanceUseCase(FolderRepository folderRepository, FolderMapper folderMapper, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.folderMapper = folderMapper;
        this.userRepository = userRepository;
    }

    public void save(Long userId, FolderSaveRequest folderSaveResquest) {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        Optional<Folder> folderFound = this.folderRepository.findByNameAndUser(folderSaveResquest.getName(), userFound.get());

        if(folderFound.isPresent())
            throw new RuntimeException("Pasta já existe na base de dados");

        Folder folder = this.folderMapper.folderSaveRequestToFolder(folderSaveResquest, userFound.get());

        this.folderRepository.save(folder);

    }

    public void update(Long userId, Long folderId, FolderUpdateRequest folderUpdateRequest) {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        Optional<Folder> folderFound = this.folderRepository.findByIdAndUser(folderId, userFound.get());

        if(folderFound.isEmpty())
            throw new RuntimeException("Pasta não existe na base de dados");

        Optional<Folder> folderNameFound = this.folderRepository.findByNameAndUser(folderUpdateRequest.getName(), userFound.get());

        if(folderNameFound.isPresent() && Objects.equals(folderFound.get().getId(), folderNameFound.get().getId()))
            return;

        Folder folder = this.folderMapper.folderUpdateRequestToFolder(folderFound.get(), folderUpdateRequest);

        this.folderRepository.save(folder);

    }

    public void delete(Long userId, Long folderId) {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        Optional<Folder> folderFound = this.folderRepository.findByIdAndUser(folderId, userFound.get());

        if(folderFound.isEmpty())
            throw new RuntimeException("Pasta não existe na base de dados");

        this.folderRepository.delete(folderFound.get());

    }


}
