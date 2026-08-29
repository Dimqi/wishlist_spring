package com.example.demo_pet_spring.service;

import com.example.demo_pet_spring.dataTransferObjects.ApiResponseDto;
import com.example.demo_pet_spring.dataTransferObjects.WishDto;
import com.example.demo_pet_spring.dataTransferObjects.WishRequestDTO;
import com.example.demo_pet_spring.entities.TagEntity;
import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.entities.WishEntity;
import com.example.demo_pet_spring.exception.BadCredentialsException;
import com.example.demo_pet_spring.exception.WishDoesNotExistException;
import com.example.demo_pet_spring.repository.UserRepository;
import com.example.demo_pet_spring.repository.WishListRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final TagService tagService;
    private final UserRepository userRepository;

    public WishListService(WishListRepository wishListRepository, TagService tagService, UserRepository userRepository){
        this.wishListRepository = wishListRepository;
        this.tagService = tagService;
        this.userRepository = userRepository;
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
        WishDto wishResponse = new WishDto(wish, true);

        return createResponse(true, 201, "wish successfully added!", wishResponse, null);

    }

    public ApiResponseDto<WishDto> getById(Long id, UserEntity currentUser){
        Long userId = currentUser.getId();

        Optional<WishEntity> wishOpt = wishListRepository.findById(id, userId);

        if (wishOpt.isPresent()) {
            return createResponse(true, 200, "wish successfully found!", new WishDto(wishOpt.get(), true), null);
        } else {
            return createResponse(false, 404, "wish not found", null, null);
        }
    }

    public ApiResponseDto<WishDto> deleteWish(Long id, UserEntity currentUser){
        Long userId = currentUser.getId();

        wishListRepository.deleteById(id, userId);
        return createResponse(true, 204, "wish successfully deleted!", null, null);

    }

    public ApiResponseDto<WishDto>  getAllWishes(UserEntity currentUser, boolean isOwner){
        Long userId = currentUser.getId();
        List<WishEntity> wishes = wishListRepository.findAll(userId);

        List<WishDto> wishDtoList = wishes.stream()
                .map(wish -> new WishDto(wish, isOwner))
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
                .map(wish -> new WishDto(wish, true))
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
        return createResponse(true, 200, "wishes successfully found!", new WishDto(wish, true), null);

    }

    @Transactional
    public ApiResponseDto<WishDto> reserveWish(Long wishId, UserEntity currentUser, String shareToken){
        WishEntity wish = wishListRepository.findByIdWithReservedUsers(wishId)
                .orElseThrow(()->WishDoesNotExistException.createWishDoesNotExistException("wish does not exist"));

        UserEntity owner = wish.getCreatedBy();

        if(owner.equals(currentUser)){
            throw BadCredentialsException.createBadCredentialsException("You can not reserve your own wish");
        }

        UserEntity user = userRepository.findByToken(shareToken)
                .orElseThrow(() -> BadCredentialsException.createBadCredentialsException("Invalid share token"));
        Hibernate.initialize(user);
        Hibernate.initialize(owner);
        if(!owner.equals(user)){
            throw BadCredentialsException.createBadCredentialsException("You can not reserve this wish");
        }


        Set<UserEntity> reservedBy = wish.getReservedBy();
        if(reservedBy.contains(currentUser)){
            throw BadCredentialsException.createBadCredentialsException("You already reserve this wish");
        }

        Set<UserEntity> reserved = wish.getReservedBy();
        reserved.add(currentUser);
        wish.setReservedBy(reserved);

        wishListRepository.save(wish);
        return createResponse(true, 200, "wishes successfully reserve!", new WishDto(wish, false), null);

    }

    @Transactional
    public ApiResponseDto<WishDto> unReserveWish(Long wishId, UserEntity currentUser){
        WishEntity wish = wishListRepository.findByIdWithReservedUsers(wishId)
                .orElseThrow(()->WishDoesNotExistException.createWishDoesNotExistException("wish does not exist"));

        Set<UserEntity> reservedBy = wish.getReservedBy();
        if(!reservedBy.contains(currentUser)){
            throw BadCredentialsException.createBadCredentialsException("You does not reserve this wish");
        }

        reservedBy.remove(currentUser);
        wish.setReservedBy(reservedBy);
        wishListRepository.save(wish);
        return createResponse(true, 200, "you successfully does not reserve wish!", new WishDto(wish, false), null);

    }



    public ApiResponseDto<WishDto> createResponse(boolean success, int code, String message, WishDto data, List<WishDto> listData ){
        ApiResponseDto<WishDto> responseDTO = new ApiResponseDto<>();
        responseDTO.setSuccess(success);
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        responseDTO.setData(data);
        responseDTO.setListData(listData);
        return responseDTO;
    }




}
