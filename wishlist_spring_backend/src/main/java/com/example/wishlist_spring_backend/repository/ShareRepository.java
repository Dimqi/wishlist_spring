package com.example.wishlist_spring_backend.repository;

import com.example.wishlist_spring_backend.entities.ShareToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShareRepository extends JpaRepository<ShareToken, Long> {

}
