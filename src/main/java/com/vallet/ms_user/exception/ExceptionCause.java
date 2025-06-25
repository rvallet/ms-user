package com.vallet.ms_user.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionCause {
    USER_NOT_FOUND("User not found"),
    PASSWORD_INVALID("Mot de passe invalide"),
    USERNAME_ALREADY_EXISTS("Username already exists"),

    ;

    private final String message;

    ExceptionCause(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static HttpStatus getStatusFromMessage(String message) {
        if (message == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if (message.equals(ExceptionCause.PASSWORD_INVALID.getMessage())) {
            return HttpStatus.BAD_REQUEST;
        } else if (message.equals(ExceptionCause.USER_NOT_FOUND.getMessage())) {
            return HttpStatus.NOT_FOUND;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

}