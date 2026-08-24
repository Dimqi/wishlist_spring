package com.example.demo_pet_spring.dataTransferObjects;

import com.example.demo_pet_spring.entities.WishEntity;
import com.example.demo_pet_spring.entities.WishPriority;

public class WishDto {
    private Long id;

    private String name;

    private String link;

    private WishPriority wishPriority;

    public WishDto(WishEntity wishEntity){
        setId(wishEntity.getId());
        setName(wishEntity.getName());
        setLink(wishEntity.getLink());
        setWishPriority(wishEntity.getWishPriority());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
