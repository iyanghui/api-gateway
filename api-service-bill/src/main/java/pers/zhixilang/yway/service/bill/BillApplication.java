package pers.zhixilang.yway.service.bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhixilang
 * @version 1.0
 * @date 2019-09-18 10:54
 */
@SpringBootApplication
@SpringBootConfiguration
public class BillApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BillApplication.class);
        application.run(args);
    }
}
