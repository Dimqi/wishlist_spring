package com.example.demo_pet_spring.exception;

public class WishDoesNotExistException extends RuntimeException{
    private WishDoesNotExistException(String message) {
        super(message);
    }

    public static WishDoesNotExistException createWishDoesNotExistException(String message) {
        return new WishDoesNotExistException(message);
    }

}
