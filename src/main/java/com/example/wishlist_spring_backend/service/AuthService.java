package com.example.wishlist_spring_backend.service;


import com.example.wishlist_spring_backend.config.SecurityConfig;
import com.example.wishlist_spring_backend.dataTransferObjects.ApiResponseDto;
import com.example.wishlist_spring_backend.dataTransferObjects.UserDto;
import com.example.wishlist_spring_backend.exception.BadCredentialsException;
import com.example.wishlist_spring_backend.exception.UserAlreadyExistsException;
import com.example.wishlist_spring_backend.dataTransferObjects.AuthRequestDTO;
import com.example.wishlist_spring_backend.entities.UserEntity;
import com.example.wishlist_spring_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final JWTService jwtService;

    public AuthService(UserRepository userRepository, SecurityConfig securityConfig, JWTService jwtService){

        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        this.jwtService = jwtService;
    }


    public Optional<UserEntity> getUserByUsername(String username){
        return userRepository.findByUsername(username);

    }

    private void addUser(UserEntity user){
        userRepository.save(user);
    }


    public ApiResponseDto<UserDto> login(AuthRequestDTO dto){
        UserEntity user = getUserByUsername(dto.getUsername())
                .orElseThrow(() -> BadCredentialsException.createBadCredentialsException("invalid password or username"));


        if(!securityConfig.passwordEncoder().matches(dto.getPassword(), user.getPassword())){
            throw BadCredentialsException.createBadCredentialsException("invalid password or username");
        }
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setToken(createToken(user));

        return createResponse(true, 200, "successfully login", userDto, null);
    }

    @Transactional
    public ApiResponseDto<UserDto> register(AuthRequestDTO dto){
        if (getUserByUsername(dto.getUsername()).isPresent()) {
            throw UserAlreadyExistsException.createUserAlreadyExistsException("User" + dto.getUsername() + "already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(securityConfig.passwordEncoder().encode(dto.getPassword()));
        addUser(user);

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setId(user.getId());
        userDto.setToken(createToken(user));



        return  createResponse(true, 201, "successfully register", userDto, null);

    }

    private String createToken(UserEntity user){
        return jwtService.generateToken(user.getUsername() , user.getId());
    }


    private ApiResponseDto<UserDto> createResponse(boolean success, int code, String message, UserDto data, List<UserDto> listData){
        ApiResponseDto<UserDto> responseDTO = new ApiResponseDto<>();

        responseDTO.setSuccess(success);
        responseDTO.setCode(code);
        responseDTO.setMessage("message");
        responseDTO.setData(data);
        responseDTO.setListData(listData);
        return responseDTO;

    }


}
