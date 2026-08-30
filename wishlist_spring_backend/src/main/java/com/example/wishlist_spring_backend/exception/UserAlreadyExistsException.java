package com.example.wishlist_spring_backend.exception;

public class UserAlreadyExistsException extends RuntimeException{
    private UserAlreadyExistsException(String message) {
        super(message);
    }

    public static UserAlreadyExistsException createUserAlreadyExistsException(String message) {
        return new UserAlreadyExistsException(message);
    }

}
