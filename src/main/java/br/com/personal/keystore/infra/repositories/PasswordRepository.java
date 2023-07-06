package br.com.personal.keystore.infra.repositories;

import br.com.personal.keystore.infra.entities.Folder;
import br.com.personal.keystore.infra.entities.Password;
import br.com.personal.keystore.infra.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PasswordRepository extends JpaRepository<Password, Long> {

    List<Password> findAllByUser(User user);

    Optional<Password> findByIdAndUser(Long id, User user);

    List<Password> findAllByUserAndFolder(User user, Folder folder);

}
