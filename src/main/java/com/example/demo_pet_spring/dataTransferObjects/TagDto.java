package com.example.demo_pet_spring.dataTransferObjects;

import com.example.demo_pet_spring.entities.TagEntity;

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
