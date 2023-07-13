package ru.tvey.cloudserverapp.service.group;

import org.springframework.security.core.Authentication;
import ru.tvey.cloudserverapp.entity.messaging.Group;
import ru.tvey.cloudserverapp.entity.user.User;

import java.util.List;

public interface GroupService {
    Group createGroup(Authentication auth,
                      List<String>memberNames, Group group);

    Group getGroup(Long id);

    void deleteGroup(Authentication auth, Long id);

    List<User> getAllUsers(Long id);
}
