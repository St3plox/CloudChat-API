package ru.tvey.cloudserverapp.datacache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tvey.cloudserverapp.entity.UserKeyIvPair;

import java.util.concurrent.TimeUnit;


@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<UserKeyIvPair> keyCacheStore() {
        return new CacheStore<>(1, TimeUnit.MINUTES);
    }
//cacheKey = "username.'typeOfKey.toClass.toString'.groupId"
}
