package ru.tvey.cloudserverapp.service.group;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tvey.cloudserverapp.entity.messaging.Group;
import ru.tvey.cloudserverapp.entity.user.User;
import ru.tvey.cloudserverapp.exception.user.UserAuthorityException;
import ru.tvey.cloudserverapp.exception.user.UserExistsException;
import ru.tvey.cloudserverapp.repository.GroupMemberRepository;
import ru.tvey.cloudserverapp.repository.GroupRepository;
import ru.tvey.cloudserverapp.service.EntityService;
import ru.tvey.cloudserverapp.service.user.UserService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {

    private final EntityService entityService;

    private final UserService userService;

    private final GroupRepository groupRepository;

    private final GroupMemberRepository groupMemberRepository;

    @Override
    public Group createGroup(Authentication auth, Group group) {
        User sender = userService.getUser(auth.getName());

        groupRepository.save(group);
        groupMemberRepository.createGroupMember(group.getId(), sender.getId());

        return group;
    }

    @Override
    public Group getGroup(Long id) {
        return (Group) entityService.unwrapEntity(groupRepository.findById(id));
    }

    @Override
    public void deleteGroup(Authentication auth, Long groupId) {
        User user = userService.getUser(auth.getName());

        List<Long> groupIds = groupMemberRepository.findAllGroupsByUser(user.getId());

        if (groupIds.contains(groupId)) {
            groupRepository.deleteById(groupId);
        }

    }

    @Override
    public List<Long> getIdsOfGroup(Long groupId) {
        return groupMemberRepository.findAllUsersByGroup(groupId);
    }

    @Override
    public List<Long> getAllGroupsOfUser(Long userId) {
        return groupMemberRepository.findAllGroupsByUser(userId);
    }

    @Override
    public Optional<Long> getUserIdOfGroup(long userId, long groupId) {

        return Optional.ofNullable(groupMemberRepository.findUserInGroup(userId, groupId));
    }

    @Override
    public void addMember(Authentication auth, long memberId, long groupId) {

        List<Long> userIds = groupBelongCheck(auth, groupId);
        User member = userService.getUser(memberId);

        if (userIds.contains(memberId)) {
            throw new UserExistsException("User " + member.getUsername() + "is already in the group");
        }

        groupMemberRepository.createGroupMember(groupId, memberId);
    }

    @Override
    public void addMember(Authentication auth, String username, long groupId) {
        List<Long> userIds = groupBelongCheck(auth, groupId);
        User member = userService.getUser(username);

        if(userIds.contains(member.getId())){
            throw new UserExistsException("User " + member.getUsername() + "is already in the group");
        }

        groupMemberRepository.createGroupMember(groupId, member.getId());

    }

    private List<Long> groupBelongCheck(Authentication auth, long groupId) {
        User user = userService.getUser(auth.getName());

        List<Long> userIds = getIdsOfGroup(groupId);
        if (!userIds.contains(user.getId())) {
            throw new UserAuthorityException(auth.getName() + " is not in the group");
        }
        return userIds;
    }

}
