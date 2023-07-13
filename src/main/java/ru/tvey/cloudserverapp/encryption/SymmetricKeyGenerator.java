package ru.tvey.cloudserverapp.encryption;

import org.springframework.stereotype.Component;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class SymmetricKeyGenerator {
    private SecretKeySpec secretKey;

    public SymmetricKeyGenerator()
            throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {

        SecureRandom rnd = new SecureRandom();
        byte [] key = new byte [EncryptionConstants.KEY_LENGTH];
        rnd.nextBytes(key);
        this.secretKey = new SecretKeySpec(key, "AES");

    }

    public SecretKeySpec getKey(){
        return this.secretKey;
    }
}
