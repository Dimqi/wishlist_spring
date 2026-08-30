package com.example.wishlist_spring_backend.service;

import com.example.wishlist_spring_backend.dataTransferObjects.ApiResponseDto;
import com.example.wishlist_spring_backend.dataTransferObjects.WishDto;
import com.example.wishlist_spring_backend.entities.ShareToken;
import com.example.wishlist_spring_backend.entities.UserEntity;
import com.example.wishlist_spring_backend.repository.ShareRepository;
import com.example.wishlist_spring_backend.repository.UserRepository;
import com.example.wishlist_spring_backend.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ShareService {

    private final ShareTokenGeneratorService tokenGenerator;
    private final UserRepository userRepository;
    private final WishListService wishListService;
    private final ShareRepository shareRepository;

    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;

    public ShareService(ShareTokenGeneratorService tokenGenerator,
                        UserRepository userRepository,
                        WishListRepository wishListRepository, WishListService wishListService, ShareRepository shareRepository) {
        this.tokenGenerator = tokenGenerator;
        this.userRepository = userRepository;
        this.wishListService = wishListService;
        this.shareRepository = shareRepository;
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

        return wishListService.getAllWishes(user, false);
    }


}