package com.example.wishlist_spring_backend.RESTApi;


import com.example.wishlist_spring_backend.config.SecurityConfig;
import com.example.wishlist_spring_backend.dataTransferObjects.ApiResponseDto;
import com.example.wishlist_spring_backend.dataTransferObjects.AuthRequestDTO;
import com.example.wishlist_spring_backend.dataTransferObjects.UserDto;
import com.example.wishlist_spring_backend.service.AuthService;
import com.example.wishlist_spring_backend.service.JWTService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(JWTService jwtService, AuthService authService, SecurityConfig securityConfig){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<UserDto>> login(@Valid @RequestBody AuthRequestDTO reqDto){
        ApiResponseDto<UserDto> response = authService.login(reqDto);

        return ResponseEntity.status(HttpStatus.valueOf(response.getCode()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getData().getToken()).
                body(response);


    }


    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<UserDto>> register(@Valid @RequestBody AuthRequestDTO reqDto){
        ApiResponseDto<UserDto> response = authService.register(reqDto);

        return ResponseEntity.status(HttpStatus.valueOf(response.getCode()))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getData().getToken()).
                body(response);

    }





}
