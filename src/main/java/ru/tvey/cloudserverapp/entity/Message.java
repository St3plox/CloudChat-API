package ru.tvey.cloudserverapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "message")
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
    @JoinColumn(name = "sender_name", referencedColumnName = "username")
    @NotBlank
    private User senderName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipient_name", referencedColumnName = "username")
    @NotBlank
    private User recipientName;


    @ManyToOne(optional = false)
    @JoinColumn(name = "file_id", referencedColumnName = "file_id")
    private FileData fileId;

    @Column(name = "text")
    private String text;

}
