package com.example.wishlist_spring_backend.repository;


import com.example.wishlist_spring_backend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.username = :username")
    Optional<UserEntity> findByUsername(@Param("username") String username);


    @Query("SELECT u FROM UserEntity u JOIN u.shareToken st WHERE st.token = :token")
    Optional<UserEntity> findByToken(@Param("token") String token);


}
