package com.example.demo_pet_spring.RESTApi;

import com.example.demo_pet_spring.dataTransferObjects.ApiResponseDto;
import com.example.demo_pet_spring.dataTransferObjects.WishDto;
import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.service.ShareService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("api/share")
public class ShareController {

    private final ShareService shareService;

    public ShareController(ShareService shareService) {
        this.shareService = shareService;
    }

    @GetMapping("/link")
    public ResponseEntity<ApiResponseDto<String>> createLink(@AuthenticationPrincipal UserEntity currentUser){
        ApiResponseDto<String> responseDto = shareService.createLink(currentUser);
        return ResponseEntity.status(HttpStatusCode.valueOf(responseDto.getCode()))
                .body(responseDto);
    }

    @GetMapping("/getAllByToken")
    public ResponseEntity<ApiResponseDto<WishDto>> getAllByToken(@RequestParam(name="token") String token){
        ApiResponseDto<WishDto> responseDto = shareService.getWishesByToken(token);
        return ResponseEntity.status(HttpStatusCode.valueOf(responseDto.getCode()))
                .body(responseDto);

    }


}
