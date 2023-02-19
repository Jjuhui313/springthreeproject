package com.sparta.springthreeproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringthreeprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringthreeprojectApplication.class, args);
    }

}
