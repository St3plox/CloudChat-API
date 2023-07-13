package ru.tvey.cloudserverapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tvey.cloudserverapp.entity.messaging.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
