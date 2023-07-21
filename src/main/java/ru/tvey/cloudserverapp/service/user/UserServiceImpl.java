package ru.tvey.cloudserverapp.service.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tvey.cloudserverapp.encryption.AsymmetricKeyGenerator;
import ru.tvey.cloudserverapp.encryption.SymmetricCipher;
import ru.tvey.cloudserverapp.entity.KeyPairEntity;
import ru.tvey.cloudserverapp.entity.user.User;
import ru.tvey.cloudserverapp.exception.user.UserExistsException;
import ru.tvey.cloudserverapp.repository.KeyPairEntityRepository;
import ru.tvey.cloudserverapp.repository.UserRepository;
import ru.tvey.cloudserverapp.security.SecurityConstants;
import ru.tvey.cloudserverapp.service.EntityService;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EntityService entityService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AsymmetricKeyGenerator asymmetricKeyGenerator;

    private final SymmetricCipher symmetricCipher;

    private final KeyPairEntityRepository keyPairEntityRepository;

    public UserServiceImpl(UserRepository userRepository, EntityService entityService, BCryptPasswordEncoder bCryptPasswordEncoder, AsymmetricKeyGenerator asymmetricKeyGenerator, SymmetricCipher symmetricCipher, KeyPairEntityRepository keyPairEntityRepository) {
        this.userRepository = userRepository;
        this.entityService = entityService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.asymmetricKeyGenerator = asymmetricKeyGenerator;
        this.symmetricCipher = symmetricCipher;
        this.keyPairEntityRepository = keyPairEntityRepository;
    }

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return (User) entityService.unwrapEntity(user);
    }

    @Override
    public User getUser(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        return (User) entityService.unwrapEntity(user);
    }

    @Override
    public void createUser(User user) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserExistsException("User with the name " + user.getUsername() + " already exists");
        }

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        asymmetricKeyGenerator.createKeys();
        long userId = getUser(user.getUsername()).getId();

        byte[] publicKey = asymmetricKeyGenerator.getPublicKey().getEncoded();
        byte[] privateKey = asymmetricKeyGenerator.getPrivateKey().getEncoded();

        byte[] encryptedPubK  = symmetricCipher.doCrypto
                (publicKey,
                "AES/CBC/PKCS5Padding",
                        new SecretKeySpec(SecurityConstants.AES_ENCRYPTION_KEY.getBytes(), "AES"),
                        new IvParameterSpec(SecurityConstants.VECTOR.getBytes()),
                        Cipher.ENCRYPT_MODE);

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        KeyPairEntity keyPair = new KeyPairEntity();
        keyPair.setUserId(userId);
        keyPair.setPublicKey(publicKey);
        keyPair.setPrivateKey(privateKey);
        keyPair.setIv(iv);
        keyPairEntityRepository.save(keyPair);
    }

}
