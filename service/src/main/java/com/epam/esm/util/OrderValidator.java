package com.epam.esm.util;

import com.epam.esm.exception.InvalidDataExeception;

import java.util.List;

public final class OrderValidator {
    private OrderValidator() {
    }

    public static void validateForCreate(CreateParameterOrder createParameterOrder) {
        validateUserID(createParameterOrder.getUserID());
        validateCertificatesID(createParameterOrder.getGiftsId());
    }

    private static void validateUserID(int id) {
        if (id < 0) {
            throw new InvalidDataExeception("User ID must be greater than zero");
        }
    }

    private static void validateCertificatesID(List<Integer> certificatesID) {
        if (certificatesID == null) {
            throw new InvalidDataExeception("The order must consist of one or more certificates");
        }
        for (int id : certificatesID) {
            if (id < 0) {
                throw new InvalidDataExeception("Certificate ID must be greater than zero");
            }
        }
    }
}
