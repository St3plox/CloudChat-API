package ru.tvey.cloudserverapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tvey.cloudserverapp.entity.User;
import ru.tvey.cloudserverapp.service.user.UserService;

@RestController
@RequestMapping("cloud/user")
@AllArgsConstructor
@Profile("dev")
public class UserControllerDevTest {

    private UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity<HttpStatus> deleteUser() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }// Only for testing purposes

}
