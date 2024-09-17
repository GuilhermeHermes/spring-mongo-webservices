package com.guilhermehermes.services.exception;

public class ObjectNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ObjectNotFoundException(Object id) {
        super("Resource not found. Id" + id);
    }

}
