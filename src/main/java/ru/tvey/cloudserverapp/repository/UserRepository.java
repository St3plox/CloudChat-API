package ru.tvey.cloudserverapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tvey.cloudserverapp.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
