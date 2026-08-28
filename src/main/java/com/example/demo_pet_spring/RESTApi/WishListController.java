package com.example.demo_pet_spring.RESTApi;

import com.example.demo_pet_spring.dataTransferObjects.ApiResponseDto;
import com.example.demo_pet_spring.dataTransferObjects.WishDto;
import com.example.demo_pet_spring.dataTransferObjects.WishRequestDTO;
import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.entities.WishEntity;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponseDto<WishDto>>  getWishById(@PathVariable Long id,
    @AuthenticationPrincipal UserEntity currentUser){
        ApiResponseDto<WishDto> response = wishListService.getById(id, currentUser);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponseDto<WishDto>>  getAllWishes(@AuthenticationPrincipal UserEntity currentUser){
        ApiResponseDto<WishDto> response = wishListService.getAllWishes(currentUser);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);
    }


    @PostMapping
    public ResponseEntity<ApiResponseDto<WishDto>> createWish(@Valid @RequestBody WishRequestDTO wishDto,
                                                              @AuthenticationPrincipal UserEntity currentUser){
        ApiResponseDto<WishDto> response = wishListService.addWish(wishDto, currentUser);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<WishDto>>  deleteWish(@PathVariable Long id,@AuthenticationPrincipal UserEntity currentUser){
        ApiResponseDto<WishDto> response = wishListService.deleteWish(id, currentUser);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);
    }

    @GetMapping("/allByTag")
    public ResponseEntity<ApiResponseDto<WishDto>> getAllByTag(@RequestParam(name = "name") String tagName, @AuthenticationPrincipal UserEntity currentUser){
        ApiResponseDto<WishDto> response = wishListService.getAllWishesByTag(currentUser, tagName);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);

    }

    @PatchMapping("/addTagToWish")
    public ResponseEntity<ApiResponseDto<WishDto>> addTagToWish(@RequestParam String name, @RequestParam(name = "wish_id") Long wishId, @AuthenticationPrincipal UserEntity currentUser){
        ApiResponseDto<WishDto> response = wishListService.updateTagOnWish(wishId, currentUser, name);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);

    }
}
