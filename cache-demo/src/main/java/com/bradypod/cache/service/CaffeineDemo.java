package com.bradypod.cache.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.TimeUnit;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version 1.0    2019/8/19
 */
public class CaffeineDemo {

    public static void main(String[] args) throws InterruptedException {
        Cache<String, Application> cache = Caffeine.newBuilder()
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .maximumSize(4)   //~ system_code使用了位操作，不要新建过多无用的应用数据
                .build();

        System.out.println(cache.get("xiaoxue", (key) -> new Application(1l, "ew", "qwerty", 1L)));

        Application cacheObject = cache.getIfPresent("xiaoxue");

        System.out.println(cacheObject.toString());

        TimeUnit.SECONDS.sleep(10);

        System.out.println(cache.getIfPresent("xiaoxue"));
    }

    @Data
    @AllArgsConstructor
    @ToString
    static class Application {
        private Long id;
        private String appId;
        private String appSecret;
        private Long systemCode;
    }
}
