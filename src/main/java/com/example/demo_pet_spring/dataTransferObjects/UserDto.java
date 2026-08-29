package com.example.demo_pet_spring.dataTransferObjects;

import com.example.demo_pet_spring.entities.WishEntity;
import jakarta.persistence.OneToMany;

public class UserDto {
    private String username;

    private Long id;

    private String token;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
