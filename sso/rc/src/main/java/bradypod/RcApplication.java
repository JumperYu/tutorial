package bradypod;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiangmin.zeng@vistel.cn
 * @version 1.0    2019/7/29
 */
@SpringBootApplication
@RestController
public class RcApplication {

    public static void main(String[] args) {
        SpringApplication.run(RcApplication.class, args);
    }

    @GetMapping("/SetCookie")
    public void SetCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String SID = request.getParameter("SID");

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(JSON.toJSONString(cookie));
            }
        }
        Cookie cookie = new Cookie("SID", SID);
        cookie.setHttpOnly(false);
        cookie.setDomain("readingcenter.com");
        cookie.setMaxAge(-1);
        cookie.setPath("/");

        response.addCookie(cookie);

        System.out.println("set cookie into reading center.com");

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write("{}");
    }
}
