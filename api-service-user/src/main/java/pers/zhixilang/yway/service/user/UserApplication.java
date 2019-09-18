package pers.zhixilang.yway.service.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-18 10:51
 */
@SpringBootApplication
@SpringBootConfiguration
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(UserApplication.class);
        application.run(args);
    }
}
