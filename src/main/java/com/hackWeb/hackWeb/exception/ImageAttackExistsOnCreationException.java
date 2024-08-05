package com.hackWeb.hackWeb.exception;

public class ImageAttackExistsOnCreationException extends RuntimeException{
    public ImageAttackExistsOnCreationException(String message) {
        super(message);
    }

    public ImageAttackExistsOnCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
