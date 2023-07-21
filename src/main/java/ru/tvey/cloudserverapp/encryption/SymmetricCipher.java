package ru.tvey.cloudserverapp.encryption;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Component
@AllArgsConstructor
public class SymmetricCipher {

    public byte[] doCrypto(byte[] bytes, String transformation,
                           SecretKey key, IvParameterSpec iv, int opmode)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(opmode, key, iv);
        return cipher.doFinal(bytes);
    }
}
