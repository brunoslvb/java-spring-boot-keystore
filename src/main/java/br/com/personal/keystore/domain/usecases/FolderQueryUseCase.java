package br.com.personal.keystore.domain.usecases;

import br.com.personal.keystore.app.models.response.FolderResponse;
import br.com.personal.keystore.cross.mappers.FolderMapper;
import br.com.personal.keystore.infra.entities.Folder;
import br.com.personal.keystore.infra.entities.User;
import br.com.personal.keystore.infra.repositories.FolderRepository;
import br.com.personal.keystore.infra.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FolderQueryUseCase {

    FolderRepository folderRepository;

    FolderMapper folderMapper;

    UserRepository userRepository;

    public FolderQueryUseCase(FolderRepository folderRepository, FolderMapper folderMapper, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.folderMapper = folderMapper;
        this.userRepository = userRepository;
    }

    public List<FolderResponse> getAll(Long userId) {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        List<Folder> folders = this.folderRepository.findByUser(userFound.get());

        return this.folderMapper.folderToFolderResponse(folders);

    }

    public FolderResponse getById(Long userId, Long folderId) {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        Optional<Folder> folder = this.folderRepository.findByIdAndUser(folderId, userFound.get());

        if(folder.isEmpty())
            throw new RuntimeException("Pasta não existe na base de dados");

        return this.folderMapper.folderToFolderResponse(folder.get());

    }


}
