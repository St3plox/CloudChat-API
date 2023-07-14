package ru.tvey.cloudserverapp.entity.messaging;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import ru.tvey.cloudserverapp.entity.FileData;

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "file_id")
    private FileData fileId;

    @NonNull
    @NotBlank
    @Column(name = "text")
    private String text;

    @Column(name = "sender_name")
    private String senderName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private Group groupId;

    @NonNull
    @Column(name = "sent_date")
    private LocalDate date;

}
