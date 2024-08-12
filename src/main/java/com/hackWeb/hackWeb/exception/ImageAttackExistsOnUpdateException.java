package com.hackWeb.hackWeb.exception;

import com.hackWeb.hackWeb.entity.Attack;

public class ImageAttackExistsOnUpdateException extends RuntimeException{
    public ImageAttackExistsOnUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
