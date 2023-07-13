package ru.tvey.cloudserverapp.datacache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.concurrent.TimeUnit;


@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<Key> keyCacheStore() {
        return new CacheStore<>(12, TimeUnit.HOURS);
    }

}
