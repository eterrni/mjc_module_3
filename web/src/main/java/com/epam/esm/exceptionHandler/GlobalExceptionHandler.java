package com.epam.esm.exceptionHandler;

import com.epam.esm.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String INVALID_DATA_MESSAGE = "Invalid data";
    private static final String ACCESS_DENY_MESSAGE = "Access denied";

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleNotExistIdEntityException(NotExistEntityException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 30), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleDuplicateNameException(DuplicateNameException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleRemoveCertificateException(RemoveCertificateException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleInvalidDataException(InvalidDataExeception exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 50), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(new ErrorHandler(INVALID_DATA_MESSAGE, 60), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleAuthenticationException(AuthenticationException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 60), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(new ErrorHandler(ACCESS_DENY_MESSAGE, 70), HttpStatus.FORBIDDEN);
    }

}
