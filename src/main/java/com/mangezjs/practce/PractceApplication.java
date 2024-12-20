package com.mangezjs.practce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PractceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PractceApplication.class, args);
    }

}
