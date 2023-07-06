package br.com.personal.keystore.domain.usecases;

import br.com.personal.keystore.app.models.response.UserResponse;
import br.com.personal.keystore.cross.mappers.UserMapper;
import br.com.personal.keystore.infra.entities.User;
import br.com.personal.keystore.infra.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryUseCase {

    UserRepository userRepository;

    UserMapper userMapper;

    public UserQueryUseCase(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponse> getAll() {

        List<User> usersFound = this.userRepository.findAll();

        return this.userMapper.usersToUsersResponse(usersFound);

    }

    public UserResponse getById(Long id) {

        Optional<User> userFound = this.userRepository.findById(id);

        if(userFound.isEmpty())
            throw new RuntimeException("Usuário não encontrado na base de dados");

        return this.userMapper.userToUserResponse(userFound.get());

    }

}
