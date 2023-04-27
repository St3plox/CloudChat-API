package ru.tvey.cloudserverapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.tvey.cloudserverapp.entity.User;
import ru.tvey.cloudserverapp.service.user.UserService;

@RestController
@RequestMapping("cloud/user")
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }// Only for testing purposes

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user) {
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
