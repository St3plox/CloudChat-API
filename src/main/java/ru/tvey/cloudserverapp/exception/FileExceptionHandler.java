package ru.tvey.cloudserverapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.tvey.cloudserverapp.exception.file.*;
import ru.tvey.cloudserverapp.exception.user.UserExistsException;
import ru.tvey.cloudserverapp.exception.user.UserNotOwnerException;

@ControllerAdvice
public class FileExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({EntityNotFoundException.class, FileIsEmptyException.class})
    public ResponseEntity<String> handleFileNotFound(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UserNotOwnerException.class, FileSaveException.class, UserExistsException.class})
    public ResponseEntity<String> handleUserNotOwner(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
