package com.example.demo_pet_spring.dataTransferObjects;

import com.example.demo_pet_spring.entities.WishPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class WishRequestDTO {

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    private String link;

    private WishPriority wishPriority;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    public WishPriority getWishPriority() {
        return wishPriority;
    }

    public void setWishPriority(WishPriority wishPriority) {
        this.wishPriority = wishPriority;
    }
}
