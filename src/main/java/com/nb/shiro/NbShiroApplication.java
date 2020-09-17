package com.nb.shiro;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@Slf4j
@SpringBootApplication
public class NbShiroApplication {



    public static void main(String[] args) {
        SpringApplication.run(NbShiroApplication.class, args);
        log.info("======= shiro started !=========");
    }

}
