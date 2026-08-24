package com.example.demo_pet_spring.service;


import com.example.demo_pet_spring.config.SecurityConfig;
import com.example.demo_pet_spring.exception.BadCredentialsException;
import com.example.demo_pet_spring.exception.UserAlreadyExistsException;
import com.example.demo_pet_spring.dataTransferObjects.AuthRequestDTO;
import com.example.demo_pet_spring.dataTransferObjects.AuthResponseDTO;
import com.example.demo_pet_spring.entities.UserEntity;
import com.example.demo_pet_spring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public AuthResponseDTO login(AuthRequestDTO dto){
        UserEntity user = getUserByUsername(dto.getUsername())
                .orElseThrow(() -> BadCredentialsException.createBadCredentialsException("invalid password or username"));


        if(!securityConfig.passwordEncoder().matches(dto.getPassword(), user.getPassword())){
            throw BadCredentialsException.createBadCredentialsException("invalid password or username");
        }

        return createResponse(user.getUsername() , user.getId(), createToken(user));
    }

    @Transactional
    public AuthResponseDTO register(AuthRequestDTO dto){
        if (getUserByUsername(dto.getUsername()).isPresent()) {
            throw UserAlreadyExistsException.createUserAlreadyExistsException("User" + dto.getUsername() + "already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(securityConfig.passwordEncoder().encode(dto.getPassword()));
        addUser(user);



        return  createResponse(user.getUsername() , user.getId(), createToken(user));

    }

    private String createToken(UserEntity user){
        return jwtService.generateToken(user.getUsername() , user.getId());
    }


    private AuthResponseDTO createResponse(String username, Long id, String token){
        AuthResponseDTO response = new AuthResponseDTO();

        response.setUsername(username);
        response.setId(id);
        response.setToken(token);

        return response;

    }


}
