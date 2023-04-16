package ru.tvey.cloudserverapp.exception.user;

public class UserExistsException extends RuntimeException{
    public UserExistsException(String message) {
        super(message);
    }
}
