package com.vallet.ms_user.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super("Resource not found : " + message);
    }

    public NotFoundException(String message, Throwable cause) {
        super("Resource not found : " + message, cause);
    }

    public NotFoundException(Throwable cause) {
        super("Resource not found", cause);
    }

    public NotFoundException() {
        super("Resource not found");
    }

}
