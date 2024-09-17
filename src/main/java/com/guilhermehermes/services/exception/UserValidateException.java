package com.guilhermehermes.services.exception;

public class UserValidateException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public UserValidateException(String message) {
        super("Error registering user, " + message);
    }
}
