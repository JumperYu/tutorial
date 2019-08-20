package com.bradypod.cache.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version 1.0    2019/8/19
 */
@Configuration
@EnableCaching
public class CacheConfig {

    // spring-data-cache integrate with redis
//    @Bean
//    public CacheManager getCacheManager(RedisConnectionFactory connectionFactory) {
//
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofSeconds(600))
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new KryoRedisSerializer(Object.class)))
//                .disableCachingNullValues();
//
//        return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
//    }

    //    spring-data-cache integrate with caffeine
    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(localCaches().stream()
                .map(c -> new CaffeineCache(c.getCacheName(),
                        Caffeine.newBuilder().recordStats()
                                .expireAfterAccess(c.getTtl(), TimeUnit.SECONDS)
                                .maximumSize(c.getMaxSize())
                                .build()))
                .collect(Collectors.toList()));
        return cacheManager;
    }

    private List<LocalCache> localCaches() {
        return Arrays.asList(
                new LocalCache(APPLICATION_CACHE_NAME, 30, 128));
    }

    @Data
    @AllArgsConstructor
    public static class LocalCache {
        private String cacheName;
        private int ttl;
        private int maxSize;
    }

    public static final String APPLICATION_CACHE_NAME = "application";
}