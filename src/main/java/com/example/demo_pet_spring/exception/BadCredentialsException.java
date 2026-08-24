package com.example.demo_pet_spring.exception;

public class BadCredentialsException extends RuntimeException{
    private BadCredentialsException(String message) {
        super(message);
    }

    public static BadCredentialsException createBadCredentialsException(String message) {
        return new BadCredentialsException(message);
    }

}
