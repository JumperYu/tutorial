package com.bradypod.cache.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.bradypod.cache.service.CacheConfig.APPLICATION_CACHE_NAME;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version 1.0    2019/8/19
 */
@Service
@Slf4j
public class ApplicationService {

    static Map<String, Application> applicationMap = new HashMap<>();

    @PostConstruct
    public void init() {
        applicationMap.put("ew", new Application(1L, "ew", "qwerty", 1L));
        applicationMap.put("bss", new Application(2L, "bss", "qwerty", 2L));
        applicationMap.put("anno", new Application(3L, "anno", "qwerty", 4L));
    }

    @Cacheable(value = APPLICATION_CACHE_NAME, key = "'application'.concat(#appId)")
    public Application getApplicationById(String appId) {
        log.info("query application: {}", appId);
        return applicationMap.get(appId);
    }

    @CacheEvict(value = APPLICATION_CACHE_NAME, allEntries = true)
    public boolean update(Application application) {
        log.info("update application {}", application);
        return true;
    }
}
