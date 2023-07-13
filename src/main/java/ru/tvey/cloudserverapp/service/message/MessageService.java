package ru.tvey.cloudserverapp.service.message;

import org.springframework.security.core.Authentication;
import ru.tvey.cloudserverapp.entity.FileData;
import ru.tvey.cloudserverapp.entity.messaging.Message;

public interface MessageService {
    void sendMessage(Authentication auth, Long groupId,
                     String text, FileData file);

    Message getMessage(Authentication auth, long id);

    void deleteMessage(Authentication auth, long id);
}
