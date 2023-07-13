package ru.tvey.cloudserverapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tvey.cloudserverapp.entity.messaging.Group;

public interface GroupRepository extends CrudRepository<Group, Long>{
}
