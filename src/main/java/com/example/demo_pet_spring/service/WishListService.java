package com.example.demo_pet_spring.service;

import com.example.demo_pet_spring.dataTransferObjects.ApiResponseDto;
import com.example.demo_pet_spring.dataTransferObjects.WishDto;
import com.example.demo_pet_spring.dataTransferObjects.WishRequestDTO;
import com.example.demo_pet_spring.entities.TagEntity;
import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.entities.WishEntity;
import com.example.demo_pet_spring.exception.WishDoesNotExistException;
import com.example.demo_pet_spring.repository.WishListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;



@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final TagService tagService;

    public WishListService(WishListRepository wishListRepository, TagService tagService){
        this.wishListRepository = wishListRepository;
        this.tagService = tagService;
    }



    public ApiResponseDto<WishDto> addWish(WishRequestDTO wishDto, UserEntity currentUser){
        
        WishEntity wish = new WishEntity();
        wish.setName(wishDto.getName());
        wish.setLink(wishDto.getLink());
        wish.setWishPriority(wishDto.getWishPriority());
        wish.setCreatedBy(currentUser);

        if(wishDto.getTagName() != null){
            TagEntity tag = tagService.getTagByName(wishDto.getTagName(), currentUser)
                    .orElseGet(() -> tagService.addNewTag(wishDto.getTagName(), currentUser));
            wish.setTag(tag);
        }

        wishListRepository.save(wish);
        WishDto wishResponse = new WishDto(wish);

        return createResponse(true, 201, "wish successfully added!", wishResponse, null);

    }

    public ApiResponseDto<WishDto> getById(Long id, UserEntity currentUser){
        Long userId = currentUser.getId();

        Optional<WishEntity> wishOpt = wishListRepository.findById(id, userId);

        if (wishOpt.isPresent()) {
            return createResponse(true, 200, "wish successfully found!", new WishDto(wishOpt.get()), null);
        } else {
            return createResponse(false, 404, "wish not found", null, null);
        }
    }

    public ApiResponseDto<WishDto> deleteWish(Long id, UserEntity currentUser){
        Long userId = currentUser.getId();

        wishListRepository.deleteById(id, userId);
        return createResponse(true, 204, "wish successfully deleted!", null, null);

    }

    public ApiResponseDto<WishDto>  getAllWishes(UserEntity currentUser){
        Long userId = currentUser.getId();
        List<WishEntity> wishes = wishListRepository.findAll(userId);

        List<WishDto> wishDtoList = wishes.stream()
                .map(WishDto::new)
                .toList();

        return createResponse(true, 200, "wishes successfully found!", null, wishDtoList);

    }

    public ApiResponseDto<WishDto> getAllWishesByTag(UserEntity currentUser, String tagName){
        Long userId = currentUser.getId();
        TagEntity tag = tagService.getTagByName(tagName, currentUser)
                .orElseThrow(() -> new RuntimeException("Tag not found"));

        Long tagId = tag.getId();

        List<WishEntity> wishes = wishListRepository.findAllByTag(userId, tagId);


        List<WishDto> wishDtoList = wishes.stream()
                .map(WishDto::new)
                .toList();

        return createResponse(true, 200, "wishes successfully found!", null, wishDtoList);
    }

    @Transactional
    public ApiResponseDto<WishDto> updateTagOnWish(Long wishId, UserEntity currentUser, String tagName){
        TagEntity tag = tagService.getTagByName(tagName, currentUser)
                .orElseGet(() -> tagService.addNewTag(tagName, currentUser));


        int updated = wishListRepository.updateTagOnWish(wishId , tag.getId(), currentUser.getId());

        if (updated == 0) {
            return createResponse(false, 404, "Wish not found or access denied", null, null);
        }

        WishEntity wish = wishListRepository.findById(wishId, currentUser.getId())
                .orElseThrow(() -> WishDoesNotExistException.createWishDoesNotExistException("wish does not exist!"));
        return createResponse(true, 200, "wishes successfully found!", new WishDto(wish), null);

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
