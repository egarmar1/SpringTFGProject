package com.hackWeb.hackWeb.exception;

public class DockerConnectionException extends RuntimeException{
    public DockerConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
