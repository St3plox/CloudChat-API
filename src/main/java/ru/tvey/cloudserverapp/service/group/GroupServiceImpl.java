package ru.tvey.cloudserverapp.service.group;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tvey.cloudserverapp.entity.messaging.Group;
import ru.tvey.cloudserverapp.entity.user.User;
import ru.tvey.cloudserverapp.repository.GroupRepository;
import ru.tvey.cloudserverapp.service.EntityService;

import java.util.List;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final EntityService entityService;


    @Override
    public Group createGroup(Authentication auth,
                             List<String>memberNames, Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group getGroup(Long id) {
        return (Group) entityService.unwrapEntity(
                groupRepository.findById(id));
    }

    @Override
    public void deleteGroup(Authentication auth, Long id) {
        entityService.unwrapEntity(groupRepository.findById(id));
    }

    @Override
    public List<User> getAllUsers(Long id) {
        return null;
    }
}
