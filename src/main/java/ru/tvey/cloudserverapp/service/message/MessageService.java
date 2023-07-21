package ru.tvey.cloudserverapp.service.message;

import org.springframework.security.core.Authentication;
import ru.tvey.cloudserverapp.entity.messaging.Message;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDate;

public interface MessageService {
    void sendMessage(Authentication auth,
                      String text,
                      LocalDate date,
                      Long groupId,
                      Long fileId) throws Exception;

    Message getMessage(Authentication auth, long id) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException, InvalidKeySpecException, InvalidAlgorithmParameterException;

    void deleteMessage(Authentication auth, long id);

    void deleteMessage(long id);

    Message[] getLastNMessages(Authentication auth, long groupId, int numberOfMessages) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException;
}
