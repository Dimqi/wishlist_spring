package com.example.wishlist_spring_backend.RESTApi;

import com.example.wishlist_spring_backend.dataTransferObjects.ApiResponseDto;
import com.example.wishlist_spring_backend.dataTransferObjects.TagDto;
import com.example.wishlist_spring_backend.entities.UserEntity;
import com.example.wishlist_spring_backend.service.TagService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/addTag")
    public ResponseEntity<ApiResponseDto<TagDto>> createTag(@RequestParam String name, @AuthenticationPrincipal UserEntity currentUser ){
        ApiResponseDto<TagDto> response =  tagService.createNewTag(name, currentUser);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);
    }


    @GetMapping("/getAll")
    public ResponseEntity<ApiResponseDto<TagDto>> getTags(@AuthenticationPrincipal UserEntity currentUser ){
        ApiResponseDto<TagDto> response =  tagService.getAll(currentUser);

        return ResponseEntity.status(HttpStatusCode.valueOf(response.getCode()))
                .body(response);
    }



}
