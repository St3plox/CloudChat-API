package ru.tvey.cloudserverapp.encryption;

import org.springframework.stereotype.Component;

import java.security.*;

@Component
public class AsymmetricKeyGenerator {

    private KeyPairGenerator keyGen;
    private KeyPair pair;
    private PrivateKey privateKey;
    private PublicKey publicKey;


    public AsymmetricKeyGenerator()
            throws NoSuchAlgorithmException, NoSuchProviderException {

        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(EncryptionConstants.KEY_LENGTH);

    }

    public void createKeys() {

        this.pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();

    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }
}
