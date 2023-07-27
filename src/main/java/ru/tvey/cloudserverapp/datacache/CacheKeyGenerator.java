package ru.tvey.cloudserverapp.datacache;

import org.springframework.stereotype.Component;
import ru.tvey.cloudserverapp.security.SecurityConstants;

@Component
public class CacheKeyGenerator {

    public String generateCacheKey(String username, long groupId){

        return username
                + "."
                + SecurityConstants.CACHE_SECRET
                +"."
                +groupId;
    }
}
