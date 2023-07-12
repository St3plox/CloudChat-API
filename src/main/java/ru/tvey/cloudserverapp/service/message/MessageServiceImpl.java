package ru.tvey.cloudserverapp.service.message;

import ru.tvey.cloudserverapp.entity.messaging.Message;

public class MessageServiceImpl implements MessageService {
    @Override
    public void sendMessage(String recipient, String sender, String text) {

    }

    @Override
    public void sendMessage(String recipient, String sender, String text, long fileId) {

    }

    @Override
    public Message getMessage(long id) {
        return null;
    }

    @Override
    public void deleteMessage(long id) {

    }
}
