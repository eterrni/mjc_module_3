package com.epam.esm.util;

import com.epam.esm.dto.TagDto;

public final class TagValidator {
    private TagValidator() {
    }

    public static boolean validateForCreate(TagDto tagDTO) {
        return validateName(tagDTO.getName());
    }

    private static boolean validateName(String name) {
        final int MAX_NAME_LENGTH = 90;
        return name != null && name.length() < MAX_NAME_LENGTH;
    }

}
