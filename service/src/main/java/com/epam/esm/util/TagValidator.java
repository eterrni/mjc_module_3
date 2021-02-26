package com.epam.esm.util;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.InvalidDataExeception;

public final class TagValidator {
    private TagValidator() {
    }

    public static void validateForCreate(TagDto tagDTO) {
        validateName(tagDTO.getName());
    }

    private static void validateName(String name) {
        final int MAX_NAME_LENGTH = 90;
        if (name == null) {
            throw new InvalidDataExeception("To create a tag, a name must be specified");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new InvalidDataExeception("The tag name must not exceed 90 characters in length");
        }
    }

}
