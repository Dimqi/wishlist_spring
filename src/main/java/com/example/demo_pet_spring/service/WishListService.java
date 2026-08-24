package com.example.demo_pet_spring.service;

import com.example.demo_pet_spring.entities.WishEntity;
import com.example.demo_pet_spring.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository){
        this.wishListRepository = wishListRepository;
    }



    public void addWish(WishEntity wish){
        
        wishListRepository.save(wish);
    }

    public Optional<WishEntity> getById(Long id){
        return wishListRepository.findById(id);
    }

    public void deleteWish(Long id){
        wishListRepository.deleteById(id);
    }

    public List<WishEntity> getAllWishes(){
        return wishListRepository.findAll();
    }



}
