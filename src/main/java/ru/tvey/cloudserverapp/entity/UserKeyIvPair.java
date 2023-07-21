package ru.tvey.cloudserverapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserKeyIvPair {

    private byte[] key;

    private byte[] iv;
}
