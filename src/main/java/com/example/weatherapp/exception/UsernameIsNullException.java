package com.example.weatherapp.exception;

public class UsernameIsNullException extends RuntimeException {
    public UsernameIsNullException() {
        super("Username cannot be null or empty!");
    }
}
