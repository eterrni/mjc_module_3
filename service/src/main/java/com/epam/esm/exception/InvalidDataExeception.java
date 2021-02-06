package com.epam.esm.exception;

public class InvalidDataExeception extends RuntimeException {
    public InvalidDataExeception() {
    }

    public InvalidDataExeception(String message) {
        super(message);
    }

    public InvalidDataExeception(String message, Throwable cause) {
        super(message, cause);
    }
}
