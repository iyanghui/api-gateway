package pers.zhixilang.yway.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-10 15:29
 */
@SpringBootApplication
@SpringBootConfiguration
@ServletComponentScan
public class YWayCoreApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(YWayCoreApplication.class);
        application.run(args);
    }
}
