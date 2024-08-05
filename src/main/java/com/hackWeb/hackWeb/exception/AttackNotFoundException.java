package com.hackWeb.hackWeb.exception;

public class AttackNotFoundException extends RuntimeException{
    public AttackNotFoundException(String message) {
        super(message);
    }

    public AttackNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
