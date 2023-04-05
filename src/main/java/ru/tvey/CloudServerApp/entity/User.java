package ru.tvey.CloudServerApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
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

}
