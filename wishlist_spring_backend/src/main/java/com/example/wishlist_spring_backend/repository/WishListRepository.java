package com.example.wishlist_spring_backend.repository;


import com.example.wishlist_spring_backend.entities.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends JpaRepository<WishEntity, Long> {

    @Query("SELECT w FROM WishEntity w WHERE w.id = :id AND w.createdBy.id = :userId")
    Optional<WishEntity> findById(@Param("id") Long id, @Param("userId") Long userId);

    @Modifying
    @Query("SELECT w FROM WishEntity w WHERE w.id = :id AND w.createdBy.id = :userId")
    void deleteById(@Param("id") Long id, @Param("userId") Long userId);

    @Query("SELECT w FROM WishEntity w WHERE w.createdBy.id = :userId")
    List<WishEntity> findAll(@Param("userId") Long userId);

    @Query("SELECT w FROM WishEntity w WHERE w.tag.id = :tagId AND w.createdBy.id = :userId")
    List<WishEntity> findAllByTag(@Param("userId") Long userId, @Param("tagId") Long tagId);

    @Modifying
    @Query("UPDATE WishEntity w SET w.tag.id = :tagId WHERE w.id = :wishId AND w.createdBy.id = :userId")
    int updateTagOnWish(@Param("wishId") Long wishId,
                  @Param("tagId") Long tagId,
                  @Param("userId") Long userId);

    @Query("SELECT w FROM WishEntity w LEFT JOIN FETCH w.reservedBy WHERE w.id = :id")
    Optional<WishEntity> findByIdWithReservedUsers(@Param("id") Long id);

}
