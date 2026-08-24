package com.example.demo_pet_spring.RESTApi;

import com.example.demo_pet_spring.dataTransferObjects.ApiResponseDto;
import com.example.demo_pet_spring.dataTransferObjects.WishDto;
import com.example.demo_pet_spring.dataTransferObjects.WishRequestDTO;
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
    public ResponseEntity<ApiResponseDto<WishDto>>  getWishById(@PathVariable Long id){
        ApiResponseDto<WishDto> response = wishListService.getById(id);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto<WishDto>>  getAllWishes(){
        ApiResponseDto<WishDto> response = wishListService.getAllWishes();

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);
    }


    @PostMapping
    public ResponseEntity<ApiResponseDto<WishDto>> createWish(@RequestBody WishRequestDTO wishDto,
                                                              @AuthenticationPrincipal UserEntity currentUser){

        ApiResponseDto<WishDto> response = wishListService.addWish(wishDto, currentUser);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);

    }


    //пофиксить, чтобы желания брались у текущего пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<WishDto>>  deleteWish(@PathVariable Long id){
        ApiResponseDto<WishDto> response = wishListService.deleteWish(id);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);
    }

}
