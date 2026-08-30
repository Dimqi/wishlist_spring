package com.example.wishlist_spring_backend.exception;

public class WishDoesNotExistException extends RuntimeException{
    private WishDoesNotExistException(String message) {
        super(message);
    }

    public static WishDoesNotExistException createWishDoesNotExistException(String message) {
        return new WishDoesNotExistException(message);
    }

}
