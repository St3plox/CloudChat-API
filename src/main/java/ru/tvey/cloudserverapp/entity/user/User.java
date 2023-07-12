package ru.tvey.cloudserverapp.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor(force = true)
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @NonNull
    @NotBlank
    @Column(name = "username")
    private String username;

    @NonNull
    @NotBlank
    @Column(name = "password")
    private String password;
//
//    @JsonIgnore
//    @ManyToMany
//    @JoinTable(
//            name = "user_file",
//            joinColumns = @JoinColumn(name ="file_id", referencedColumnName = "file_id"),
//            inverseJoinColumns = @JoinColumn(name ="user_id", referencedColumnName = "user_id")
//    )
//    private Set<FileData> fileStorageSet;

}
