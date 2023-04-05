package ru.tvey.cloudserverapp.service;

import ru.tvey.cloudserverapp.entity.User;

public interface UserService {
    User getUser(Long id);

    User getUser(String username);

    User createUser(User user);

}
