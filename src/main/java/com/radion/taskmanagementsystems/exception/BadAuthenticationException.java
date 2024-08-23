package com.radion.taskmanagementsystems.exception;

public class BadAuthenticationException extends RuntimeException{
    public BadAuthenticationException(String message) {
        super(message);
    }
}
