package com.example.demo_pet_spring.service;

import com.example.demo_pet_spring.dataTransferObjects.ApiResponseDto;
import com.example.demo_pet_spring.dataTransferObjects.WishDto;
import com.example.demo_pet_spring.dataTransferObjects.WishRequestDTO;
import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.entities.WishEntity;
import com.example.demo_pet_spring.repository.WishListRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository){
        this.wishListRepository = wishListRepository;
    }



    public ApiResponseDto<WishDto> addWish(WishRequestDTO wishDto, UserEntity currentUser){
        
        WishEntity wish = new WishEntity();
        wish.setName(wishDto.getName());
        wish.setLink(wishDto.getLink());
        wish.setWishPriority(wishDto.getWishPriority());
        wish.setCreatedBy(currentUser);

        wishListRepository.save(wish);
        WishDto wishResponse = new WishDto(wish);

        return createResponse(true, 201, "wish successfully added!", wishResponse, null);

    }

    public ApiResponseDto<WishDto> getById(Long id){

        Optional<WishEntity> wishOpt = wishListRepository.findById(id);

        if (wishOpt.isPresent()) {
            return createResponse(true, 200, "wish successfully found!", new WishDto(wishOpt.get()), null);
        } else {
            return createResponse(false, 404, "wish not found", null, null);
        }
    }

    public ApiResponseDto<WishDto> deleteWish(Long id){

        wishListRepository.deleteById(id);
        return createResponse(true, 204, "wish successfully deleted!", null, null);

    }

    public ApiResponseDto<WishDto>  getAllWishes(){
        List<WishEntity> wishes = wishListRepository.findAll();

        // Преобразуем каждый элемент
        List<WishDto> wishDtoList = wishes.stream()
                .map(WishDto::new)
                .toList();

        return createResponse(true, 200, "wishes successfully found!", null, wishDtoList);

    }


    public ApiResponseDto<WishDto> createResponse(boolean success, int code, String message, WishDto data, List<WishDto> listData ){
        ApiResponseDto<WishDto> responseDTO = new ApiResponseDto<>();
        responseDTO.setSuccess(success);
        responseDTO.setCode(code);
        responseDTO.setMessage("message");
        responseDTO.setData(data);
        responseDTO.setListData(listData);
        return responseDTO;
    }

}
