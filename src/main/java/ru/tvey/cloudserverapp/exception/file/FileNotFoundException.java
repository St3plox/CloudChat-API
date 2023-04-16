package ru.tvey.cloudserverapp.exception.file;

import jakarta.persistence.EntityNotFoundException;

public class FileNotFoundException extends EntityNotFoundException {
    public FileNotFoundException(String message) {
        super(message);
    }
}
