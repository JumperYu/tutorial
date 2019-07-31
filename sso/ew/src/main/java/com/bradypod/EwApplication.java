package com.bradypod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version 1.0    2019/7/29
 */
@SpringBootApplication
@RestController
public class EwApplication {

    public static void main(String[] args) {
        SpringApplication.run(EwApplication.class, args);
    }

}
