package ru.tvey.cloudserverapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor(force = true)
@Table(name = "file")
public class FileData {

    @Column(name = "file_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    @NonNull
    private byte[] content;


    @Column(name = "name")
    @NonNull
    private String name;

    @Column(name = "filetype")
    @NonNull
    private String fileType;

    @ManyToOne(optional = false)
    @NonNull
    @JoinColumn(name = "owner_name", referencedColumnName = "username")
    private User ownerName;


}
