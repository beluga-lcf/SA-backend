package com.example.genius;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.genius.mapper")
public class GeniusApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeniusApplication.class, args);
    }

}
