package ru.tvey.cloudserverapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.tvey.cloudserverapp.entity.FileData;

public interface FileDataRepository extends CrudRepository<FileData, Long> {
}
