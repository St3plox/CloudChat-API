package ru.tvey.cloudserverapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tvey.cloudserverapp.entity.KeyPairEntity;

import java.util.Optional;

public interface KeyPairEntityRepository extends JpaRepository<KeyPairEntity, Long> {
    Optional<KeyPairEntity> findKeyPairEntityByUserId(Long userId);
}
