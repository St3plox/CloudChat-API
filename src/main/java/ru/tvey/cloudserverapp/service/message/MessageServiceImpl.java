package ru.tvey.cloudserverapp.service.message;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tvey.cloudserverapp.entity.FileData;
import ru.tvey.cloudserverapp.entity.messaging.Message;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public void sendMessage(Authentication auth, Long groupId, String text, FileData file) {

    }

    @Override
    public Message getMessage(Authentication auth, long id) {
        return null;
    }

    @Override
    public void deleteMessage(Authentication auth, long id) {

    }
}
