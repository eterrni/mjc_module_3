package com.epam.esm.repository.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.IGiftCertificateRepository;
import com.epam.esm.util.GiftCertificateCriteriaBuilder;
import com.epam.esm.util.GiftCertificateQueryParameter;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class GiftCertificateRepository implements IGiftCertificateRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificate> readAll() {
        return entityManager.createQuery("select certificate from GiftCertificate certificate", GiftCertificate.class).getResultList();
    }

    @Override
    public List<GiftCertificate> readAll(GiftCertificateQueryParameter parameter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = GiftCertificateCriteriaBuilder.getInstance().build(criteriaBuilder, parameter);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public GiftCertificate read(final Integer id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public GiftCertificate create(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public void delete(final Integer id) {
        entityManager.remove(read(id));
    }


}
