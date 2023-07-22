package ru.tvey.cloudserverapp.service.group;

import org.springframework.security.core.Authentication;
import ru.tvey.cloudserverapp.entity.messaging.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    Group createGroup(Authentication auth, Group group);

    Group getGroup(Long id);

    void deleteGroup(Authentication auth, Long id);

    List<Long> getIdsOfGroup(Long groupId);

    List<Long> getAllGroupsOfUser(Long userId);

    Optional<Long> getUserIdOfGroup(long userId, long groupId);

    void addMember(Authentication auth, long memberId, long groupId);

    void addMember(Authentication auth, String username, long groupId);

}
