package ru.tvey.cloudserverapp.service.message;

import ru.tvey.cloudserverapp.entity.Message;

public interface MessageService {
    void sendMessage(String recipient, String sender, String text);

    void sendMessage(String recipient, String sender, String text, long fileId);

    Message getMessage(long id);

    void deleteMessage(long id);
}
