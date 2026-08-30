package com.example.wishlist_spring_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WishlistSpringApplication {


    public static void main(String[] args) {
        SpringApplication.run(WishlistSpringApplication.class, args);
    }


}
