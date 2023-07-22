package ru.tvey.cloudserverapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.tvey.cloudserverapp.entity.messaging.Group;
import ru.tvey.cloudserverapp.service.group.GroupService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cloud/group")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("{id}")
    public ResponseEntity<Group> getGroup(@PathVariable long id) {
        return new ResponseEntity<>(groupService.getGroup(id), HttpStatus.OK);
    }

    @GetMapping("/members/{groupId}")
    public ResponseEntity<List<Long>> getAllMemberIdsOfGroup(@PathVariable long groupId){
        return new ResponseEntity<>(groupService.getIdsOfGroup(groupId), HttpStatus.OK);
    }

    @GetMapping("/groupsof/{userId}")
    public ResponseEntity<List<Long>> getAllGroupsOfUser(@PathVariable long userId){
        return new ResponseEntity<>(groupService.getAllGroupsOfUser(userId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteGroup(@PathVariable long id, Authentication auth) {
        groupService.deleteGroup(auth ,id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Group> createGroup(Authentication auth, @RequestBody Group group) {
        return new ResponseEntity<>(groupService.createGroup(auth, group), HttpStatus.CREATED);
    }

    @PostMapping("/member/id/{memberId}")
    public ResponseEntity<HttpStatus> addGroupMemberById(Authentication auth, Group group, @PathVariable long memberId) {
        groupService.addMember(auth, memberId, group.getId());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/member/name/{username}")
    public ResponseEntity<Group> addGroupMember(Authentication auth, Group group, @PathVariable String username) {
        groupService.addMember(auth, username, group.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
