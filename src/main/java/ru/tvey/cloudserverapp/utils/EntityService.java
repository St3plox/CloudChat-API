package ru.tvey.cloudserverapp.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.tvey.cloudserverapp.exception.file.EntityNotFoundException;

import java.util.Optional;

@Service
public class EntityService {


    public <T> Object unwrapEntity(Optional<T> entity) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException("Entity not found");
    }

    @Bean
    public EntityService appUtils() {
        return new EntityService();
    }
}
