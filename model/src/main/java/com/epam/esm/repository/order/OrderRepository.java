package com.epam.esm.repository.order;

import com.epam.esm.entity.Order;
import com.epam.esm.repository.IOrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderRepository implements IOrderRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> readAll(int offset, int limit) {
        CriteriaQuery<Order> query = entityManager.getCriteriaBuilder().createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root);
        return entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public Order read(int id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> readOrdersByUserID(int userID) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        query.select(root).where(criteriaBuilder.equal(root.get("user"), userID));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Order create(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public long getCountOfEntities() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(Order.class)));
        return entityManager.createQuery(query).getSingleResult();
    }
}
