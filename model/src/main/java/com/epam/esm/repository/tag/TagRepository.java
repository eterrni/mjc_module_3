package com.epam.esm.repository.tag;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.repository.ITagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements ITagRepository {

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
    public Tag getMostWidelyUsedTagFromUser(int userID) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> tagQuery = criteriaBuilder.createQuery(Tag.class);
        Root<User> userRoot = tagQuery.from(User.class);

        ListJoin<User, Order> orderList = userRoot.joinList("orderList");
        ListJoin<Order, GiftCertificate> giftList = orderList.joinList("giftCertificateList");
        ListJoin<GiftCertificate, Tag> tagList = giftList.joinList("tags");

        Expression orderID = tagList.get("id");
        tagQuery.select(tagList)
                .where(criteriaBuilder.equal(userRoot.get("id"), userID))
                .groupBy(orderID)
                .orderBy(criteriaBuilder.desc(criteriaBuilder.count(orderID)));

        return entityManager.createQuery(tagQuery).setMaxResults(1).getSingleResult();
    }

}
