package ru.tvey.cloudserverapp.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.tvey.cloudserverapp.entity.messaging.Group;
import ru.tvey.cloudserverapp.entity.messaging.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    default List<Message> findLastNMessagesSortedByLocalDateAndGroup(int n, Group groupId) {
        PageRequest pageRequest = PageRequest.of(0, n, Sort.by(Sort.Direction.DESC, "date", "groupId"));
        return findAllByGroupIdOrderByDateDesc(groupId, pageRequest);
    }
    List<Message> findAllByGroupIdOrderByDateDesc(Group groupId, PageRequest pageRequest);
}
