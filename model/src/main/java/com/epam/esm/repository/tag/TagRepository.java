package com.epam.esm.repository.tag;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.ITagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements ITagRepository {

    private static final String SELECT_MOST_USED_TAG = "WITH user_with_biggest_cost AS \n" +
            "(SELECT sum(price) AS orders_cost,\n" +
            "user_id_user AS ui FROM orders\n" +
            "GROUP BY user_id_user ORDER BY orders_cost DESC LIMIT 1)\n" +
            "SELECT tag.id_tag, tag.name_tag FROM tag\n" +
            "JOIN gift_certificate_has_tag ON tag.id_tag = gift_certificate_has_tag.tag_id_tag\n" +
            "JOIN gift_certificate ON gift_certificate_has_tag.gift_certificate_id_gift_certificate = gift_certificate.id\n" +
            "JOIN gift_certificate_has_orders ON gift_certificate.id = gift_certificate_has_orders.gift_certificate_id\n" +
            "JOIN orders ON gift_certificate_has_orders.orders_id_order = orders.id_order\n" +
            "JOIN user_with_biggest_cost on orders.user_id_user = user_with_biggest_cost.ui\n" +
            "GROUP BY (tag.id_tag) ORDER BY COUNT(tag.id_tag) DESC LIMIT 1;";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Tag> readAll(int offset, int limit) {
        CriteriaQuery<Tag> query = entityManager.getCriteriaBuilder().createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        return entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Tag read(final Integer tagId) {
        return entityManager.find(Tag.class, tagId);
    }

    @Override
    public Optional<Tag> readTagByName(String tagName) {
        return entityManager.createQuery("select tag From Tag tag where name=:tagName")
                .setParameter("tagName", tagName)
                .getResultStream().findFirst();
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public void delete(final Integer tagId) {
        entityManager.remove(read(tagId));
    }

    @Override
    public long getCountOfEntities() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(Tag.class)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Tag getMostWidelyUsedTagFromUser() {
        return (Tag) entityManager.createNativeQuery(SELECT_MOST_USED_TAG, Tag.class).getSingleResult();
    }

}
