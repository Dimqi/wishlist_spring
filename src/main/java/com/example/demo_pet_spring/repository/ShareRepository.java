package com.example.demo_pet_spring.repository;

import com.example.demo_pet_spring.entities.ShareToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<ShareToken, Long> {

}
