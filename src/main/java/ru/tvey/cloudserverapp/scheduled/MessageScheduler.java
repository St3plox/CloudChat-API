package ru.tvey.cloudserverapp.scheduled;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.tvey.cloudserverapp.datacache.CacheStore;
import ru.tvey.cloudserverapp.entity.UserKeyIvPair;
import ru.tvey.cloudserverapp.entity.messaging.Group;
import ru.tvey.cloudserverapp.entity.messaging.Message;
import ru.tvey.cloudserverapp.repository.MessageRepository;
import ru.tvey.cloudserverapp.security.SecurityConstants;
import ru.tvey.cloudserverapp.service.EntityService;
import ru.tvey.cloudserverapp.service.group.GroupService;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class MessageScheduler {

    private final GroupService groupService;

    private final MessageRepository messageRepository;

    private final CacheStore<UserKeyIvPair> cacheStore;

    private final EntityService entityService;

    private final ScheduledExecutorService executor =
            Executors.newSingleThreadScheduledExecutor();


    public void deleteMessages(long groupId) throws InterruptedException {

        Group group = groupService.getGroup(groupId);
        List<Long> messageIds = messageRepository.findMessageIdsByGroupId(group);

        if (messageIds.isEmpty()) return;

        Message message =
                (Message) entityService.unwrapEntity(messageRepository.findById(messageIds.get(0)));

        String cacheKey = message.getSenderName() + "." + SecurityConstants.CACHE_SECRET;

        UserKeyIvPair userKeyIvPair;

        int i = 0;

        do {
            userKeyIvPair =
                    cacheStore.get(cacheKey);

            Thread.sleep(60000);// 1 minute
            i++;
            if(i >= 6){
                throw new RuntimeException("Wait limit exceed, key is still present");
            }
        }while (userKeyIvPair != null);

        for (long id : messageIds) {
            messageRepository.deleteById(id);
        }
    }

    public void scheduleWithArgument(long argument, long delayInHours) {
        executor.schedule(() -> {
            try {
                deleteMessages(argument);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, delayInHours, TimeUnit.MINUTES);
    }
}
