package com.example.demo_pet_spring.RESTApi;

import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.entities.WishEntity;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.demo_pet_spring.service.WishListService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/wishes")
public class WishListController {

    public WishListService wishListService;

    public WishListController(WishListService wishListService){
        this.wishListService = wishListService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<WishEntity> getWishById(@PathVariable Long id){
        Optional<WishEntity> wishOpt = wishListService.getById(id);

        if (wishOpt.isPresent()) {
            return ResponseEntity.ok().body(wishOpt.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<WishEntity>> getAllWishes(){
        return ResponseEntity.ok().body(wishListService.getAllWishes());
    }


    @PostMapping
    public ResponseEntity<WishEntity> createWish(@RequestBody WishEntity wish,
    @AuthenticationPrincipal UserEntity currentUser){

        wish.setCreatedBy(currentUser);
        wishListService.addWish(wish);

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(wish);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWish(@PathVariable Long id){
        wishListService.deleteWish(id);

        return ResponseEntity.noContent().build();
    }

}
