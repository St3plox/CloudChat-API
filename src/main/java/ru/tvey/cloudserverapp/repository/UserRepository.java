package ru.tvey.cloudserverapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tvey.cloudserverapp.entity.user.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
