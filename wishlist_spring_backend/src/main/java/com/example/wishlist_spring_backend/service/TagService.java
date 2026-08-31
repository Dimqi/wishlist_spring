package com.example.wishlist_spring_backend.service;

import com.example.wishlist_spring_backend.dataTransferObjects.ApiResponseDto;
import com.example.wishlist_spring_backend.dataTransferObjects.TagDto;
import com.example.wishlist_spring_backend.entities.TagEntity;
import com.example.wishlist_spring_backend.entities.UserEntity;
import com.example.wishlist_spring_backend.repository.TagRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    public TagEntity addNewTag(String name, UserEntity user){
        TagEntity tag = new TagEntity();
        tag.setName(name);
        tag.setUser(user);

        tagRepository.save(tag);
        return tag;
    }

    @Transactional
    @CacheEvict(value = "tags", key = "#user.id")
    public ApiResponseDto<TagDto> createNewTag(String name, UserEntity user){
        if(tagRepository.findTagEntitiesByName(name, user.getId()).isPresent()){
            return  createResponse(false, 400, "Tag with this name already exists", null, null);
        }

        TagEntity tag = addNewTag(name, user);
        TagDto tagDto = new TagDto(tag);

        return createResponse(true, 201, "tag successfully added!", tagDto, null);


    }

    @Cacheable(value = "tags", key = "#user.id + '_' + #name")
    public Optional<TagEntity> getTagByName(String name, UserEntity user){
        return tagRepository.findTagEntitiesByName(name, user.getId());
    }

    @Cacheable(value = "tags", key = "#user.id")
    public ApiResponseDto<TagDto> getAll(UserEntity user){
        List<TagEntity> tags = tagRepository.findAll(user.getId());

        List<TagDto> tagsDto = tags.stream()
                .map(TagDto::new)
                .toList();

        return createResponse(true, 200, "tags successfully found!", null, tagsDto);

    }



    public ApiResponseDto<TagDto> createResponse(boolean success, int code, String message, TagDto data, List<TagDto> listData ){
        ApiResponseDto<TagDto> responseDTO = new ApiResponseDto<>();
        responseDTO.setSuccess(success);
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        responseDTO.setData(data);
        responseDTO.setListData(listData);
        return responseDTO;
    }



}
