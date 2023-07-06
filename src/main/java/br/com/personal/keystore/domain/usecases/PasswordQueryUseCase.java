package br.com.personal.keystore.domain.usecases;

import br.com.personal.keystore.app.models.response.PasswordResponse;
import br.com.personal.keystore.cross.mappers.PasswordMapper;
import br.com.personal.keystore.infra.entities.Folder;
import br.com.personal.keystore.infra.entities.Password;
import br.com.personal.keystore.infra.entities.User;
import br.com.personal.keystore.infra.repositories.FolderRepository;
import br.com.personal.keystore.infra.repositories.PasswordRepository;
import br.com.personal.keystore.infra.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PasswordQueryUseCase {

    PasswordRepository passwordRepository;

    PasswordMapper passwordMapper;

    UserRepository userRepository;

    FolderRepository folderRepository;

    public PasswordQueryUseCase(PasswordRepository passwordRepository, PasswordMapper passwordMapper, UserRepository userRepository, FolderRepository folderRepository) {
        this.passwordRepository = passwordRepository;
        this.passwordMapper = passwordMapper;
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
    }

    public List<PasswordResponse> getAll(Long userId) {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        List<Password> usersFound = this.passwordRepository.findAllByUser(userFound.get());

        return this.passwordMapper.passwordToPasswordResponse(usersFound);

    }

    public PasswordResponse getById(Long userId, Long passwordId) {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        Optional<Password> passwordFound = this.passwordRepository.findByIdAndUser(passwordId, userFound.get());

        if(passwordFound.isEmpty())
            throw new RuntimeException("Password não encontrado na base de dados");

        return this.passwordMapper.passwordToPasswordResponse(passwordFound.get());

    }

    public List<PasswordResponse> getByFolder(Long userId, Long folderId) {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        Optional<Folder> folderFound = this.folderRepository.findByIdAndUser(folderId, userFound.get());

        if(folderFound.isEmpty())
            throw new RuntimeException("Pasta não existe na base de dados");

        List<Password> passwords = this.passwordRepository.findAllByUserAndFolder(userFound.get(), folderFound.get());

        return this.passwordMapper.passwordToPasswordResponse(passwords);

    }

}
