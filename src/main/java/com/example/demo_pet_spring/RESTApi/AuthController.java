package com.example.demo_pet_spring.RESTApi;


import com.example.demo_pet_spring.config.SecurityConfig;
import com.example.demo_pet_spring.dataTransferObjects.AuthRequestDTO;
import com.example.demo_pet_spring.dataTransferObjects.AuthResponseDTO;
import com.example.demo_pet_spring.service.AuthService;
import com.example.demo_pet_spring.service.JWTService;
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
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO reqDto){
        AuthResponseDTO response = authService.login(reqDto);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken()).
                body(response);


    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO reqDto){
        AuthResponseDTO response = authService.register(reqDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + response.getToken()).
                body(response);


    }





}
