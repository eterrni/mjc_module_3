package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDto;

import java.math.BigDecimal;

public final class GiftCertificateValidator {
    private GiftCertificateValidator() {
    }

    public static boolean validateForCreate(GiftCertificateDto giftCertificateDto) {
        if (!validateName(giftCertificateDto.getName())) {
            return false;
        }
        if (!validateDescription(giftCertificateDto.getDescription())) {
            return false;
        }
        if (!validateDuration(giftCertificateDto.getDuration())) {
            return false;
        }
        return validatePrice(giftCertificateDto.getPrice());
    }

    public static boolean validateForUpdate(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() != null) {
            return validateName(giftCertificateDto.getName());
        }
        if (giftCertificateDto.getPrice() != null) {
            return validatePrice(giftCertificateDto.getPrice());
        }
        if (giftCertificateDto.getDuration() != null) {
            return validateDuration(giftCertificateDto.getDuration());
        }
        return validateDescription(giftCertificateDto.getDescription());
    }

    private static boolean validateName(String name) {
        final int MAX_SIZE_NAME = 90;

        return name != null && name.length() < MAX_SIZE_NAME;
    }

    private static boolean validateDescription(String description) {
        final int MAX_SIZE_DESCRIPTION = 90;

        return description != null && description.length() < MAX_SIZE_DESCRIPTION;
    }

    private static boolean validatePrice(BigDecimal price) {
        return price.doubleValue() > 0;
    }

    private static boolean validateDuration(Integer duration) {
        return duration != null && duration > 0;
    }
}
