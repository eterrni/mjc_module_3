package com.epam.esm.repository.certificate;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.IGiftCertificateRepository;
import com.epam.esm.repository.tag.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;

@Repository
public class GiftCertificateRepository implements IGiftCertificateRepository {

    private static final String SELECT_TAGS_BY_CERTIFICATE_ID = "SELECT id_tag,name_tag FROM gift_certificate_has_tag\n" +
            "join tag\n" +
            "on gift_certificate_has_tag.tag_id_tag = tag.id_tag\n" +
            "where gift_certificate_id_gift_certificate=?;";
    private static final String GET_BY_QUERY_PARAMETERS = "SELECT * FROM gift_certificate \n" +
            "LEFT JOIN gift_certificate_has_tag ON gift_certificate.id = gift_certificate_has_tag.gift_certificate_id_gift_certificate\n" +
            "LEFT JOIN tag ON gift_certificate_has_tag.tag_id_tag = tag.id_tag \n" +
            "WHERE tag.name_tag LIKE concat(?, '%') AND \n" +
            "gift_certificate.name LIKE concat(?, '%') AND\n" +
            "gift_certificate.description LIKE concat(?, '%') GROUP BY gift_certificate.id ";

    private final JdbcTemplate jdbcTemplate;

    private final GiftCertificateMapper giftCertificateMapper;

    private final TagMapper tagMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public GiftCertificateRepository(JdbcTemplate jdbcTemplate, GiftCertificateMapper giftCertificateMapper, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.giftCertificateMapper = giftCertificateMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<GiftCertificate> readAll() {
        return entityManager.createQuery("select certificate from GiftCertificate certificate", GiftCertificate.class).getResultList();
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

    @Override
    public List<GiftCertificate> readByQueryParameters(HashMap<String, String> parameters) {
        String query = GET_BY_QUERY_PARAMETERS + parameters.get("sortType") +
                " " + parameters.get("orderType");
        return jdbcTemplate.query(query, new Object[]{
                parameters.get("tagName"),
                parameters.get("name"),
                parameters.get("description"),
        }, giftCertificateMapper);
    }

    @Override
    public void joinCertificatesAndTags(List<GiftCertificate> giftCertificates) {
        for (GiftCertificate giftCertificate : giftCertificates) {
            List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_BY_CERTIFICATE_ID, new Object[]{giftCertificate.getId()}, tagMapper);
            giftCertificate.setTags(tags);
        }
    }

}
