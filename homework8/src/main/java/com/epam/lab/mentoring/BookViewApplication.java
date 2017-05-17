package com.epam.lab.mentoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.epam.lab.mentoring" })
public class BookViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookViewApplication.class, args);
    }
}

