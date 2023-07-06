package br.com.personal.keystore.domain.usecases;

import br.com.personal.keystore.app.models.request.PasswordSaveRequest;
import br.com.personal.keystore.app.models.request.PasswordUpdateRequest;
import br.com.personal.keystore.cross.mappers.PasswordMapper;
import br.com.personal.keystore.cross.utils.PasswordAESEncryption;
import br.com.personal.keystore.infra.entities.Folder;
import br.com.personal.keystore.infra.entities.Password;
import br.com.personal.keystore.infra.entities.User;
import br.com.personal.keystore.infra.repositories.FolderRepository;
import br.com.personal.keystore.infra.repositories.PasswordRepository;
import br.com.personal.keystore.infra.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordMaintenanceUseCase {

    PasswordRepository passwordRepository;

    PasswordMapper passwordMapper;

    UserRepository userRepository;

    FolderRepository folderRepository;

    public PasswordMaintenanceUseCase(PasswordRepository passwordRepository, PasswordMapper passwordMapper, UserRepository userRepository, FolderRepository folderRepository) {
        this.passwordRepository = passwordRepository;
        this.passwordMapper = passwordMapper;
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
    }

    public void save(Long userId, PasswordSaveRequest passwordSaveRequest) throws Exception {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        Optional<Folder> folderFound = this.folderRepository.findById(passwordSaveRequest.getFolderId());

        if(folderFound.isEmpty())
            throw new RuntimeException("Pasta não existe na base de dados");

        PasswordAESEncryption passwordAESEncryption = new PasswordAESEncryption();

        passwordSaveRequest.setPassword(passwordAESEncryption.encrypt(passwordSaveRequest.getPassword()));

        Password password = this.passwordMapper.passwordSaveRequestToPassword(
            passwordSaveRequest, userFound.get(), folderFound.get()
        );

        this.passwordRepository.save(password);

    }

    public void update(Long userId, Long passwordId, PasswordUpdateRequest passwordUpdateRequest) {

        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        Optional<Password> passwordFound = this.passwordRepository.findById(passwordId);

        if(passwordFound.isEmpty())
            throw new RuntimeException("Senha não existe na base de dados");

        Optional<Folder> folderFound = this.folderRepository.findById(passwordUpdateRequest.getFolderId());

        if(folderFound.isEmpty())
            throw new RuntimeException("Pasta não existe na base de dados");

        Password password = this.passwordMapper.passwordUpdateRequestToPassword(
            passwordFound.get(), passwordUpdateRequest, folderFound.get()
        );

        this.passwordRepository.save(password);

    }

    public void delete(Long userId, Long passwordId) {
        Optional<User> userFound = this.userRepository.findById(userId);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não existe na base de dados");

        Optional<Password> passwordFound = this.passwordRepository.findById(passwordId);

        if(passwordFound.isEmpty())
            throw new RuntimeException("Senha não existe na base de dados");

        this.passwordRepository.delete(passwordFound.get());

    }




}
