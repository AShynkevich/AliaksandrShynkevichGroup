package com.epam.lab.mentoring;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan(basePackages = { "com.epam.lab.mentoring" })
@EnableEmailTools
@EnableAsync
public class BookViewApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookViewApplication.class, args);
    }
}
