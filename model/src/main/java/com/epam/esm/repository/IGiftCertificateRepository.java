package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.util.GiftCertificateQueryParameter;

import java.util.List;

/**
 * An interface that inherits the CRD interface and complements it with the methods required for the gift certificate
 *
 * @author Alexander Novikov
 */
public interface IGiftCertificateRepository extends ICRDRepository<GiftCertificate, Integer> {
    /**
     * Update entity
     *
     * @param entity modified
     * @return Number of affected rows when updated
     */
    GiftCertificate update(final GiftCertificate entity);

    /**
     * Get all entities from database
     *
     * @param parameter {@link GiftCertificateQueryParameter} Data object containing params for request
     * @return List of matched {@link GiftCertificate} entities from database.
     */

    List<GiftCertificate> readAll(GiftCertificateQueryParameter parameter, int page, int size);

    /**
     * Get count of exist gift certificates.
     *
     * @return number of exist gift certificates
     */
    long getCountOfEntities();

    /**
     * Get count of exist gift certificates which correspond to the parameters.
     *
     * @return number of exist gift certificates which correspond to the parameters.
     */
    long getCountOfEntities(GiftCertificateQueryParameter parameter);

}
