package com.example.demo_pet_spring.repository;


import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.entities.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishEntity, Long> {



}
