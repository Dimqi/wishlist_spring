package com.example.wishlist_spring_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "wishes")
public class WishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String link;

    @Enumerated(EnumType.STRING)
    private WishPriority wishPriority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private TagEntity tag;

    @ManyToMany
    @JoinTable(name="wish_reservation",
            joinColumns = @JoinColumn(name = "wish_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> reservedBy = new HashSet<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public TagEntity getTag() {
        return tag;
    }

    public void setTag(TagEntity tag) {
        this.tag = tag;
    }

    public Set<UserEntity> getReservedBy() {
        return reservedBy;
    }

    public void setReservedBy(Set<UserEntity> reservedBy) {
        this.reservedBy = reservedBy;
    }
}
