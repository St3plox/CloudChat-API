package ru.tvey.cloudserverapp.service.message;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tvey.cloudserverapp.datacache.CacheStore;
import ru.tvey.cloudserverapp.encryption.AsymmetricCipher;
import ru.tvey.cloudserverapp.encryption.EncryptionConstants;
import ru.tvey.cloudserverapp.encryption.SymmetricCipher;
import ru.tvey.cloudserverapp.encryption.SymmetricKeyGenerator;
import ru.tvey.cloudserverapp.entity.KeyPairEntity;
import ru.tvey.cloudserverapp.entity.UserKeyIvPair;
import ru.tvey.cloudserverapp.entity.messaging.Group;
import ru.tvey.cloudserverapp.entity.messaging.Message;
import ru.tvey.cloudserverapp.entity.user.User;
import ru.tvey.cloudserverapp.exception.cache.CacheException;
import ru.tvey.cloudserverapp.exception.user.UserAuthorityException;
import ru.tvey.cloudserverapp.repository.KeyPairEntityRepository;
import ru.tvey.cloudserverapp.repository.MessageRepository;
import ru.tvey.cloudserverapp.scheduled.MessageScheduler;
import ru.tvey.cloudserverapp.security.SecurityConstants;
import ru.tvey.cloudserverapp.service.EntityService;
import ru.tvey.cloudserverapp.service.file.FileService;
import ru.tvey.cloudserverapp.service.group.GroupService;
import ru.tvey.cloudserverapp.service.user.UserService;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final GroupService groupService;

    private final FileService fileService;

    private final UserService userService;

    private final SymmetricKeyGenerator symmetricKeyGenerator;

    private final CacheStore<UserKeyIvPair> keyCacheStore;

    private final EntityService entityService;

    private final AsymmetricCipher asymmetricCipher;

    private final SymmetricCipher symmetricCipher;

    private final KeyPairEntityRepository keyPairEntityRepository;

    private final MessageScheduler messageScheduler;


    @Override
    public void sendMessage(Authentication auth, String text,
                            LocalDate date, Long groupId, Long fileId) throws Exception {

        long senderId = userService.getUser(auth.getName()).getId();

        if (groupService.getUserIdOfGroup(senderId, groupId).isEmpty()) {
            throw new RuntimeException("message sender is not in group");
        }

        Message message = new Message();

        message.setDate(date);


        if (fileId != null) {
            message.setFileId(fileService.serveFile(fileId, auth));
        }
        Group group = groupService.getGroup(groupId);
        message.setGroupId(group);
        message.setSenderName(auth.getName());

        String keyForSenderCache =
                auth.getName() + "." + SecurityConstants.CACHE_SECRET
                        + "." + message.getGroupId();


        KeyPairEntity senderKeyPairEntity = (KeyPairEntity) entityService.
                unwrapEntity(keyPairEntityRepository.
                        findKeyPairEntityByUserId(senderId));

        byte[] publicKeyBytes = senderKeyPairEntity.getPublicKey();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey senderPublicKey = keyFactory.generatePublic(keySpec);


        //check for existing keys
        if (keyCacheStore.get(keyForSenderCache) != null) {

            UserKeyIvPair secretKeyIv = keyCacheStore.get(keyForSenderCache);

            byte[] encryptedSecretKeyB = secretKeyIv.getKey();
            byte[] ivB = secretKeyIv.getIv();

            byte[] privateKeyBytes = senderKeyPairEntity.getPrivateKey();
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey senderPrivateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

            byte[] decKeyB = asymmetricCipher.doCrypto(Cipher.DECRYPT_MODE, senderPrivateKey, encryptedSecretKeyB);

            byte[] encTextBytes = symmetricCipher
                    .doCrypto(text.getBytes(),
                            EncryptionConstants.AES_ALGO,
                            new SecretKeySpec(decKeyB, "AES"),
                            new IvParameterSpec(ivB),
                            Cipher.ENCRYPT_MODE);

            message.setText(Base64.getEncoder().encodeToString(encTextBytes));
            messageRepository.save(message);
            return;
        }

        //encrypting message with generated K
        SecretKey secretKey = symmetricKeyGenerator.getKey();
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        byte[] encryptedTextBytes = symmetricCipher.doCrypto(text.getBytes(), EncryptionConstants.AES_ALGO,
                secretKey, ivParameterSpec, Cipher.ENCRYPT_MODE);
        text = Base64.getEncoder().encodeToString(encryptedTextBytes);

        message.setText(text);

        //Encrypting every group user with his public key


        byte[] encryptedKeyBytes = asymmetricCipher.doCrypto(Cipher.ENCRYPT_MODE, senderPublicKey, secretKey.getEncoded());

        UserKeyIvPair secretKeyIvPair = new UserKeyIvPair(encryptedKeyBytes, iv);// secretKeyIvString = key.iv

        keyCacheStore.add(keyForSenderCache, secretKeyIvPair);

        List<Long> groupUserIds = groupService.getIdsOfGroup(groupId);
//        User[] users = new User[groupUserIds.size()];

        for (long id : groupUserIds) {
            if (id != senderId) {
                User user = userService.getUser(id);
                String keyCacheSBuilder = user.getUsername() +
                        '.' +
                        SecurityConstants.CACHE_SECRET
                        + "." + message.getGroupId();

                KeyPairEntity userKPE = (KeyPairEntity) entityService.
                        unwrapEntity(keyPairEntityRepository.
                                findKeyPairEntityByUserId(user.getId()));

                byte[] userPublicKeyBytes = userKPE.getPublicKey();
                X509EncodedKeySpec userKeySpec = new X509EncodedKeySpec(userPublicKeyBytes);
                KeyFactory userKeyFactory = KeyFactory.getInstance("RSA");
                PublicKey userPublicKey = userKeyFactory.generatePublic(userKeySpec);

                byte[] userEncryptedKeyBytes = asymmetricCipher
                        .doCrypto(Cipher.ENCRYPT_MODE, userPublicKey, secretKey.getEncoded());


                UserKeyIvPair userKeyIvPair = new UserKeyIvPair(userEncryptedKeyBytes, iv);

                keyCacheStore.add(keyCacheSBuilder, userKeyIvPair);
            }
        }
        messageRepository.save(message);
        messageScheduler.scheduleWithArgument(message.getGroupId().getId(), 12);//
    }
    //cacheKey = "username.cache_secret"

    public Message getMessage(Authentication auth, long messageId) throws
            NoSuchPaddingException, IllegalBlockSizeException,
            NoSuchAlgorithmException, BadPaddingException,
            InvalidKeyException, InvalidKeySpecException,
            InvalidAlgorithmParameterException {

        Message message = (Message) entityService.unwrapEntity(messageRepository.findById(messageId));

        User user = userService.getUser(auth.getName());
        List<Long> userIds = groupService.getIdsOfGroup(message.getGroupId().getId());

        if (!userIds.contains(user.getId())) {
            throw new UserAuthorityException("user does not belong to the group");
        }

        String text = message.getText();

        UserKeyIvPair userKeyIvPair =
                keyCacheStore.get(auth.getName() + "."
                        + SecurityConstants.CACHE_SECRET +
                        "." + message.getGroupId());

        if (userKeyIvPair == null) {
            throw new CacheException("there is no such message key in the cache");
        }

        KeyPairEntity keyPair = (KeyPairEntity) entityService.
                unwrapEntity(keyPairEntityRepository.findKeyPairEntityByUserId(user.getId()));

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = kf.generatePrivate
                (new PKCS8EncodedKeySpec(keyPair.getPrivateKey()));

        byte[] decSymKeyBytes = asymmetricCipher.doCrypto(Cipher.DECRYPT_MODE, privateKey, userKeyIvPair.getKey());

        SecretKey secretKey = new SecretKeySpec(decSymKeyBytes, "AES");

        byte[] textBytes = Base64.getDecoder().decode(text);

        byte[] decTextBytes = symmetricCipher.doCrypto(textBytes,
                EncryptionConstants.AES_ALGO,
                secretKey,
                new IvParameterSpec(userKeyIvPair.getIv()),
                Cipher.DECRYPT_MODE);

        message.setText(new String(decTextBytes));

        return message;
    }

    @Override
    public void deleteMessage(Authentication auth, long id) {

        Message message = (Message) entityService.unwrapEntity(messageRepository.findById(id));

        if (!Objects.equals(message.getSenderName(), auth.getName())) {
            throw new UserAuthorityException("User is not message sender");
        }

        messageRepository.delete(message);
    }

    @Override
    public void deleteMessage(long id) {
        Message message = (Message) entityService.unwrapEntity(messageRepository.findById(id));

        messageRepository.delete(message);
    }


    @Override
    public Message[] getLastNMessages(Authentication auth, long groupId, int numberOfMessages)
            throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException,
            BadPaddingException, InvalidKeySpecException,
            InvalidKeyException {

        List<Long> userIds = groupService.getIdsOfGroup(groupId);
        User user = userService.getUser(auth.getName());
        if (!userIds.contains(user.getId())) {
            throw new UserAuthorityException("user does not belong to the group");
        }

        Group group = groupService.getGroup(groupId);

        List<Message> encryptedMessageList = messageRepository.
                findLastNMessagesSortedByLocalDateAndGroup(numberOfMessages, group);

        Message[] decryptedMessages = new Message[encryptedMessageList.size()];

        for (int i = 0; i < decryptedMessages.length; i++) {
            decryptedMessages[i] = getMessage(auth, encryptedMessageList.get(i).getId());
        }

        return decryptedMessages;
    }


}