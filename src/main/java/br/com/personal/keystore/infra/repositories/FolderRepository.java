package br.com.personal.keystore.infra.repositories;

import br.com.personal.keystore.infra.entities.Folder;
import br.com.personal.keystore.infra.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findByUser(User user);

    Optional<Folder> findByIdAndUser(Long id, User user);

    Optional<Folder> findByNameAndUser(String name, User user);

}
