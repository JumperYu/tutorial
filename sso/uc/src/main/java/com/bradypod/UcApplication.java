package com.bradypod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version 1.0    2019/7/29
 */
@SpringBootApplication
@RestController
public class UcApplication {

    public static void main(String[] args) {
        SpringApplication.run(UcApplication.class, args);
    }

    @GetMapping("/Login")
    public void Login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie cookieForSid = new Cookie("SID", String.valueOf(System.currentTimeMillis()));
        cookieForSid.setDomain("vistel.com");
        cookieForSid.setHttpOnly(false);
        cookieForSid.setMaxAge(-1);
        cookieForSid.setPath("/");

        Cookie cookieForToken = new Cookie("Token", UUID.randomUUID().toString().replaceAll("-", ""));
        cookieForToken.setDomain("vistel.com");
        cookieForToken.setHttpOnly(true);
        cookieForToken.setMaxAge(-1);
        cookieForToken.setPath("/");

        response.addCookie(cookieForSid);
        response.addCookie(cookieForToken);

        response.sendRedirect("http://uc.vistel.com/jump.html?redirectUrl=http://ew.vistel.com");
    }
}
