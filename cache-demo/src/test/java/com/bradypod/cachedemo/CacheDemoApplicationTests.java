package com.bradypod.cachedemo;

import com.bradypod.cache.CacheDemoApplication;
import com.bradypod.cache.service.Application;
import com.bradypod.cache.service.ApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CacheDemoApplication.class})
public class CacheDemoApplicationTests {

    @Autowired
    ApplicationService applicationService;

    @Test
    public void test01() {
        applicationService.getApplicationById("ew"); // first

        applicationService.update(new Application(1L, "", " ", 1L));

        applicationService.getApplicationById("ew"); // no hit

        applicationService.getApplicationById("ew"); // hit
    }

}
