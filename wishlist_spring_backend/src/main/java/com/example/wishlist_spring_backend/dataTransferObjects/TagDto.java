package com.example.wishlist_spring_backend.dataTransferObjects;

import com.example.wishlist_spring_backend.entities.TagEntity;

public class TagDto {

    private String name;

    public TagDto(TagEntity tag){
        this.name = tag.getName();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
