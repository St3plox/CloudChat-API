package ru.tvey.cloudserverapp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GroupMemberRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public List<Long> findAllGroupsByUser(Long userId) {
        Query query = entityManager.createNativeQuery("SELECT group_id FROM group_member WHERE user_id = :userId");
        query.setParameter("userId", userId);
        List<BigInteger> groupIds = query.getResultList();
        List<Long> longList = groupIds.stream().map(BigInteger::longValue).collect(Collectors.toList());
        return longList;
    }

    public List<Long> findAllUsersByGroup(Long groupId) {
        Query query = entityManager.createNativeQuery("SELECT user_id FROM group_member WHERE group_id = :groupId");
        query.setParameter("groupId", groupId);
        List<BigInteger> userIds = query.getResultList();
        List<Long> longList = userIds.stream().map(BigInteger::longValue).collect(Collectors.toList());
        return longList;
    }

    @Transactional
    public void createGroupMember(Long groupId, Long userId) {
        Query query = entityManager.createNativeQuery("INSERT INTO group_member (group_id, user_id) VALUES (:groupId, :userId)");
        query.setParameter("groupId", groupId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }

    @Transactional
    public void deleteGroupMember(Long groupId, Long userId) {
        Query query = entityManager.createNativeQuery("DELETE FROM group_member WHERE group_id = :groupId AND user_id = :userId");
        query.setParameter("groupId", groupId);
        query.setParameter("userId", userId);
        query.executeUpdate();
    }
}

