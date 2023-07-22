package ru.tvey.cloudserverapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tvey.cloudserverapp.exception.cache.CacheException;
import ru.tvey.cloudserverapp.exception.user.UserAuthorityException;

@ControllerAdvice
public class MessagingExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({CacheException.class})
    public ResponseEntity<String> handleCache(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserAuthorityException.class})
    public ResponseEntity<String> handleUserAuthority(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}