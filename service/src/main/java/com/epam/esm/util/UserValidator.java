package com.epam.esm.util;

import com.epam.esm.dto.RegistrationUserDto;
import com.epam.esm.exception.InvalidDataExeception;

import java.util.Objects;

public final class UserValidator {

    private static final String EMAIL_REGEXP = "^(?=.{3,30}$)[^\\s]+@[^\\s]+\\.[^\\s]+$";
    private static final int MIN_LENGTH_NAME = 2;
    private static final int MAX_LENGTH_NAME = 45;

    private static final int MIN_LENGTH_PASSWORD = 5;
    private static final int MAX_LENGTH_PASSWORD = 20;

    private UserValidator() {
    }

    public static void validateRegistrationUser(RegistrationUserDto registrationUserDto) {
        validateName(registrationUserDto.getName());
        validateEmail(registrationUserDto.getEmail());
        validatePassword(registrationUserDto.getPassword());
    }

    private static void validateName(String name) {
        if (Objects.isNull(name)) {
            throw new InvalidDataExeception("To register, enter a name");
        }
        if (name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            throw new InvalidDataExeception("The length of the name can not be less than 2 characters and more than 45 characters");
        }
    }

    private static void validateEmail(String email) {
        if (Objects.isNull(email)) {
            throw new InvalidDataExeception("To register, enter a email");
        }
        if (!email.matches(EMAIL_REGEXP)) {
            throw new InvalidDataExeception("Pattern of email : xx@xx.xx");
        }
    }

    private static void validatePassword(String password) {
        if (Objects.isNull(password)){
            throw new InvalidDataExeception("To register, enter a password");
        }
        if(password.length() < MIN_LENGTH_PASSWORD || password.length() > MAX_LENGTH_PASSWORD){
            throw new InvalidDataExeception("The length of the password can not be less than 5 characters and more than 20 characters");
        }
    }
}
