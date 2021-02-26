package com.epam.esm.exception;

public class RemoveCertificateException extends RuntimeException {
    public RemoveCertificateException() {
    }

    public RemoveCertificateException(String message) {
        super(message);
    }

    public RemoveCertificateException(String message, Throwable cause) {
        super(message, cause);
    }
}
