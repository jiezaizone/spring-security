package com.isuwang.security.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SecurityAppApplication {

    public static void main(String[] args){
        SpringApplication.run(SecurityAppApplication.class, args);
    }

}
