package ru.tvey.cloudserverapp.encryption;

import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

@Component
public class SymmetricKeyGenerator {
    private final SecretKeySpec secretKey;

    public SymmetricKeyGenerator() {

        SecureRandom rnd = new SecureRandom();
        byte [] key = new byte [32];
        rnd.nextBytes(key);
        this.secretKey = new SecretKeySpec(key, "AES");
    }

    public SecretKeySpec getKey(){
        return this.secretKey;
    }
}
