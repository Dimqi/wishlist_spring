package com.example.demo_pet_spring.repository;


import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.entities.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishEntity, Long> {

    @Query("SELECT w FROM WishEntity w WHERE w.id = :id AND w.createdBy.id = :userId")
    Optional<WishEntity> findById(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT w FROM WishEntity w WHERE w.id = :id AND w.createdBy.id = :userId")
    void deleteById(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT w FROM WishEntity w WHERE w.createdBy.id = :userId")
    List<WishEntity> findAll(@Param("userId") Long userId);
}
