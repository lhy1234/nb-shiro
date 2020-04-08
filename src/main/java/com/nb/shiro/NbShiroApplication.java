package com.nb.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NbShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbShiroApplication.class, args);
        System.out.println("======= shiro =========");
    }

}
