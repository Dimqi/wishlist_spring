package com.example.wishlist_spring_backend.dataTransferObjects;

import com.example.wishlist_spring_backend.entities.UserEntity;
import com.example.wishlist_spring_backend.entities.WishEntity;
import com.example.wishlist_spring_backend.entities.WishPriority;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WishDto {
    private Long id;

    private String name;

    private String link;

    private WishPriority wishPriority;

    private String tagName;

    private List<String> reservedByUsernames = new ArrayList<>();

    @JsonCreator
    public WishDto(){}

    @JsonIgnore
    public WishDto(WishEntity wishEntity, boolean isOwner){
        setId(wishEntity.getId());
        setName(wishEntity.getName());
        setLink(wishEntity.getLink());
        setWishPriority(wishEntity.getWishPriority());

        if(wishEntity.getTag() != null){
            setTagName(wishEntity.getTag().getName());
        }

        if (!isOwner && !wishEntity.getReservedBy().isEmpty()) {
            this.reservedByUsernames = wishEntity.getReservedBy().stream()
                    .map(UserEntity::getUsername)
                    .collect(Collectors.toList());


        }
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }


    public List<String> getReservedByUsernames() {
        return reservedByUsernames;
    }

    public void setReservedByUsernames(List<String> reservedByUsernames) {
        this.reservedByUsernames = reservedByUsernames;
    }
}
