package com.epam.esm.exception;

public class NotExistEntityException extends RuntimeException {
    public NotExistEntityException() {
    }

    public NotExistEntityException(String message) {
        super(message);
    }

    public NotExistEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
