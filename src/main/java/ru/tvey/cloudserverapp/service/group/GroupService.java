package ru.tvey.cloudserverapp.service.group;

import ru.tvey.cloudserverapp.entity.messaging.Group;
import ru.tvey.cloudserverapp.entity.user.User;

import java.util.List;

public interface GroupService {
    Group createGroup(Group group);

    Group getGroup(Long id);

    void deleteGroup(Long id);

    List<User> getAllUsers(Long id);
}
