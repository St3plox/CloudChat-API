package ru.tvey.cloudserverapp.exception.file;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }
}
