package com.epam.esm.util;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.exception.InvalidDataExeception;

import java.math.BigDecimal;
import java.util.Objects;

public final class GiftCertificateValidator {
    private GiftCertificateValidator() {
    }

    public static void validateForCreate(GiftCertificateDto giftCertificateDto) {
        validateName(giftCertificateDto.getName());
        validateDescription(giftCertificateDto.getDescription());
        validateDuration(giftCertificateDto.getDuration());
        validatePrice(giftCertificateDto.getPrice());
    }

    public static void validateForUpdate(GiftCertificateDto giftCertificateDto) {
        if (Objects.nonNull(giftCertificateDto.getName())) {
            validateName(giftCertificateDto.getName());
        }
        if (Objects.nonNull(giftCertificateDto.getPrice())) {
            validatePrice(giftCertificateDto.getPrice());
        }
        if (Objects.nonNull(giftCertificateDto.getDuration())) {
            validateDuration(giftCertificateDto.getDuration());
        }
        if (Objects.nonNull(giftCertificateDto.getDescription())) {
            validateDescription(giftCertificateDto.getDescription());
        }
    }

    private static void validateName(String name) {
        final int MAX_SIZE_NAME = 90;
        if (name == null) {
            throw new InvalidDataExeception("To create a certificate, a name must be specified");
        }
        if (name.length() > MAX_SIZE_NAME) {
            throw new InvalidDataExeception("Name must not exceed 90 characters in length");
        }
    }

    private static void validateDescription(String description) {
        final int MAX_SIZE_DESCRIPTION = 90;

        if (description == null) {
            throw new InvalidDataExeception("To create a certificate, a description must be specified");
        }
        if (description.length() > MAX_SIZE_DESCRIPTION) {
            throw new InvalidDataExeception("Description must not exceed 90 characters in length");
        }
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new InvalidDataExeception("To create a certificate, a price must be specified");
        }
        if (price.doubleValue() < 0) {
            throw new InvalidDataExeception("The price can't be less than zero");
        }
    }

    private static void validateDuration(Integer duration) {
        if (duration == null) {
            throw new InvalidDataExeception("To create a certificate, a duration must be specified");
        }
        if (duration < 0 || duration > 365) {
            throw new InvalidDataExeception("The duration can't be less than 0 and more than 365");
        }
    }
}
