package com.example.weatherapp.exception;

public class PasswordIsNullException extends RuntimeException {
    public PasswordIsNullException() {
        super("Password cannot be null or empty!");
    }
}
