package com.hackWeb.hackWeb.exception;

public class OAuth2UserNotFoundException extends RuntimeException{

    public OAuth2UserNotFoundException(String message) {
        super(message);
    }

    public OAuth2UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
