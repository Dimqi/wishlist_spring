package com.example.wishlist_spring_backend.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tags")
public class TagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;


    @OneToMany(mappedBy = "tag", fetch = FetchType.LAZY)
    private List<WishEntity> wish;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<WishEntity> getWish() {
        return wish;
    }

    public void setWish(List<WishEntity> wish) {
        this.wish = wish;
    }
}
