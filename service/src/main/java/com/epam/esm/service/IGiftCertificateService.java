package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.util.GiftCertificateQueryParameter;

import java.util.List;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the gift certificate
 *
 * @author Alexander Novikov
 */
public interface IGiftCertificateService extends ICRDService<GiftCertificateDto, Integer> {
    /**
     * Update entity
     *
     * @param entity modified
     */
    GiftCertificateDto update(final GiftCertificateDto entity);

    /**
     * Invokes DAO method to get List of all GiftCertificates that matches parameters
     *
     * @param parameter is {@link GiftCertificateQueryParameter} object with requested parameters
     * @return List of {@link GiftCertificateDto} objects with GiftCertificate data.
     */
    List<GiftCertificateDto> readAll(GiftCertificateQueryParameter parameter, int page, int size);

    /**
     * Get count of exist gift certificates.
     *
     * @return number of exist gift certificates
     */
    long getCountOfEntities(GiftCertificateQueryParameter parameter);
}
