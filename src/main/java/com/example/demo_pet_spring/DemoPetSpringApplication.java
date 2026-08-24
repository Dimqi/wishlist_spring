package com.example.demo_pet_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoPetSpringApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoPetSpringApplication.class, args);
    }


}
