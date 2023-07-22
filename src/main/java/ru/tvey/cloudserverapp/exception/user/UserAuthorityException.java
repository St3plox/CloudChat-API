package ru.tvey.cloudserverapp.exception.user;

public class UserAuthorityException extends RuntimeException {
    public UserAuthorityException(String message){
        super(message);
    }
}
