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
    public List<Order> readAll() {
        return entityManager.createQuery("select order from Order order", Order.class).getResultList();
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
}
