package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDto;

import java.math.BigDecimal;
import java.util.Objects;

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
        if (Objects.nonNull(giftCertificateDto.getName()) && !validateName(giftCertificateDto.getName())) {
            return false;
        }
        if (Objects.nonNull(giftCertificateDto.getPrice()) && !validatePrice(giftCertificateDto.getPrice())) {
            return false;
        }
        if (Objects.nonNull(giftCertificateDto.getDuration()) && !validateDuration(giftCertificateDto.getDuration())) {
            return false;
        }
        if (Objects.nonNull(giftCertificateDto.getDescription())) {
            return validateDescription(giftCertificateDto.getDescription());
        }
        return true;
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
