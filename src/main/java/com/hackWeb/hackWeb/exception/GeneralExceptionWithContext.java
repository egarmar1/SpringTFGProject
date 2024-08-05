package com.hackWeb.hackWeb.exception;

public class GeneralExceptionWithContext extends RuntimeException {
    private final String context; // Esta es la vista a la que quieres redirigir

    public GeneralExceptionWithContext(String message, Throwable cause, String context) {
        super(message, cause);
        this.context = context;
    }

    public GeneralExceptionWithContext(String message, String context) {
        super(message);
        this.context = context;
    }

    public String getContext() {
        return context;
    }


}
