package com.example.demo_pet_spring.repository;

import com.example.demo_pet_spring.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    @Query("SELECT t FROM TagEntity t WHERE t.name =:name AND t.user.id = :id" )
    Optional<TagEntity> findTagEntitiesByName(String name, Long id);

    @Query("SELECT t FROM TagEntity t WHERE t.user.id = :userId")
    List<TagEntity> findAll(Long id);
}
