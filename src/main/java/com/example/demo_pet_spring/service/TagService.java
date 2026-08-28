package com.example.demo_pet_spring.service;

import com.example.demo_pet_spring.dataTransferObjects.ApiResponseDto;
import com.example.demo_pet_spring.dataTransferObjects.WishDto;
import com.example.demo_pet_spring.entities.TagEntity;
import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.repository.TagRepository;
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
    //пофиксить lazy загрузку
    public TagEntity addNewTag(String name, UserEntity user){
        TagEntity tag = new TagEntity();
        tag.setName(name);
        tag.setUser(user);

        tagRepository.save(tag);
        return tag;
    }

    @Transactional
    public ApiResponseDto<TagEntity> createNewTag(String name, UserEntity user){
        if(tagRepository.findTagEntitiesByName(name, user.getId()).isPresent()){
            return  createResponse(false, 400, "Tag with this name already exists", null, null);
        }

        TagEntity tag = addNewTag(name, user);

        return createResponse(true, 201, "tag successfully added!", tag, null);


    }


    public Optional<TagEntity> getTagByName(String name, UserEntity user){
        return tagRepository.findTagEntitiesByName(name, user.getId());
    }

    public ApiResponseDto<TagEntity> getAll(UserEntity user){
        List<TagEntity> tags = tagRepository.findAll(user.getId());

        return createResponse(true, 200, "tags successfully found!", null, tags);


    }



    public ApiResponseDto<TagEntity> createResponse(boolean success, int code, String message, TagEntity data, List<TagEntity> listData ){
        ApiResponseDto<TagEntity> responseDTO = new ApiResponseDto<>();
        responseDTO.setSuccess(success);
        responseDTO.setCode(code);
        responseDTO.setMessage(message);
        responseDTO.setData(data);
        responseDTO.setListData(listData);
        return responseDTO;
    }



}
