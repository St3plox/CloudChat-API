package ru.tvey.CloudServerApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@Table(name = "files")
public class FileStorage {

    @Column(name = "file_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "content")
    byte[] content;


    @Column(name = "name")
    @NonNull
    private String name;


}
