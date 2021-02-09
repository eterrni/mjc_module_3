package com.epam.esm.repository.user;

import com.epam.esm.entity.User;
import com.epam.esm.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

    private static final String SELECT_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS = "select orders.user from Order orders " +
            "group by orders.user order by sum(orders.price) desc";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> readAll(int offset, int limit) {
        CriteriaQuery<User> query = entityManager.getCriteriaBuilder().createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        return entityManager.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public User read(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public long getCountOfEntities() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(User.class)));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public User getUserWithHighestCostOfAllOrders() {
        return (User) entityManager.createQuery(SELECT_USER_WITH_HIGHEST_COST_OF_ALL_ORDERS)
                .setMaxResults(1).getSingleResult();
    }
}
