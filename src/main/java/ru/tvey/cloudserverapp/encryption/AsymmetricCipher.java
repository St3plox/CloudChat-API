package ru.tvey.cloudserverapp.encryption;

import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

@Component
public class AsymmetricCipher {
    public byte[] doCrypto(int opmode, Key key, byte[] bytes) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(opmode, key);
        return cipher.doFinal(bytes);
    }
}
