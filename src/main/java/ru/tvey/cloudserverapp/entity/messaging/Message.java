package ru.tvey.cloudserverapp.entity.messaging;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.tvey.cloudserverapp.entity.FileData;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
public class Message {
    public Message() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "file_id", referencedColumnName = "file_id")
    private FileData fileId;

    @Column(name = "text")
    private String text;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private Group groupId;
}