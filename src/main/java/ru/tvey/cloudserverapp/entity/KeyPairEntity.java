package ru.tvey.cloudserverapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "user_secret_key_pair")
@RequiredArgsConstructor
public class KeyPairEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "user_id")
    private long userId;


    @Column(name = "public_key")
    private byte[] publicKey;

    @Column(name = "private_key")
    private byte[] privateKey;

    @Column(name = "iv")
    private byte[] iv;
}
