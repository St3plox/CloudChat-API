package ru.tvey.cloudserverapp.service.user;

import ru.tvey.cloudserverapp.entity.user.User;

public interface UserService {
    User getUser(Long id);

    User getUser(String username);

    void createUser(User user);

}
