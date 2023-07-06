package br.com.personal.keystore.domain.usecases;

import br.com.personal.keystore.app.models.request.UserSaveRequest;
import br.com.personal.keystore.app.models.request.UserUpdateRequest;
import br.com.personal.keystore.cross.utils.BCryptPassword;
import br.com.personal.keystore.cross.mappers.UserMapper;
import br.com.personal.keystore.infra.entities.Folder;
import br.com.personal.keystore.infra.entities.User;
import br.com.personal.keystore.infra.repositories.FolderRepository;
import br.com.personal.keystore.infra.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserMaintenanceUseCase {

    UserRepository userRepository;

    UserMapper userMapper;

    FolderRepository folderRepository;

    public UserMaintenanceUseCase(UserRepository userRepository, UserMapper userMapper, FolderRepository folderRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.folderRepository = folderRepository;
    }

    public void save(UserSaveRequest userSaveRequest) {

        if (this.userRepository.findByEmail(userSaveRequest.getEmail()).isPresent())
            throw new RuntimeException("Email já existente na base de dados");

        BCryptPassword bCryptPassword = new BCryptPassword();

        userSaveRequest.setPassword(bCryptPassword.encode(userSaveRequest.getPassword()));

        User user = this.userMapper.userSaveRequestToUser(userSaveRequest);

        Folder folder = new Folder();
        folder.setName("General");
        folder.setUser(user);

        user.setFolders(List.of(folder));

        this.userRepository.save(user);

    }

    public void update(Long id, UserUpdateRequest userUpdateRequest) {

        Optional<User> userFound = this.userRepository.findById(id);

        if (userFound.isEmpty())
            throw new RuntimeException("Usuário não encontrado na base de dados");

        User user = this.userMapper.userUpdateRequestToUser(userFound.get(), userUpdateRequest);

        this.userRepository.save(user);

    }

    public void delete(Long id) {

        Optional<User> userFound = this.userRepository.findById(id);

        if (userFound.isEmpty())
            throw new RuntimeException("Usuário não encontrado na base de dados");

        this.userRepository.delete(userFound.get());

    }

}
