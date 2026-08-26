package com.example.demo_pet_spring.service;

import com.example.demo_pet_spring.dataTransferObjects.ApiResponseDto;
import com.example.demo_pet_spring.dataTransferObjects.WishDto;
import com.example.demo_pet_spring.entities.ShareToken;
import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.entities.WishEntity;
import com.example.demo_pet_spring.repository.UserRepository;
import com.example.demo_pet_spring.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ShareService {

    private final ShareTokenGeneratorService tokenGenerator;
    private final UserRepository userRepository;
    private final WishListService wishListService;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public ShareService(ShareTokenGeneratorService tokenGenerator,
                        UserRepository userRepository,
                        WishListRepository wishListRepository, WishListService wishListService) {
        this.tokenGenerator = tokenGenerator;
        this.userRepository = userRepository;
        this.wishListService = wishListService;
    }


    public ApiResponseDto<String> createLink(UserEntity currentUser) {
        ShareToken shareToken = tokenGenerator.generateShareToken();

        currentUser.setShareToken(shareToken);
        shareToken.setUser(currentUser);

        userRepository.save(currentUser);

        String link = baseUrl + "/api/share/getAllByToken?token=" + shareToken.getToken();

        ApiResponseDto<String> responseDto = new ApiResponseDto<>();
        responseDto.setCode(200);
        responseDto.setMessage("Link successfully created");
        responseDto.setSuccess(true);
        responseDto.setData(link);

        return responseDto;
    }

    @Transactional(readOnly = true)
    public ApiResponseDto<WishDto> getWishesByToken(String token) {
        UserEntity user = userRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        return wishListService.getAllWishes(user);
    }


}