package com.example.demo_pet_spring.service;

import com.example.demo_pet_spring.entities.ShareToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class ShareTokenGeneratorService {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final int TOKEN_LENGTH = 32;

    @Value("${share.expiration.days:30}")
    private int expirationDays;

    public ShareToken generateShareToken() {

        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        SECURE_RANDOM.nextBytes(tokenBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);

        LocalDateTime expiresAt = LocalDateTime.now().plusDays(expirationDays);

        return new ShareToken(token, expiresAt);
    }
}