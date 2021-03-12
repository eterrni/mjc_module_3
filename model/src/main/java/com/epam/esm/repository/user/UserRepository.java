package com.epam.esm.repository.user;

import com.epam.esm.entity.User;
import com.epam.esm.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

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
    public User read(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User create(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        entityManager.remove(read(id));
    }

    @Override
    public User readByEmail(String email) {
        TypedQuery<User> userTypedQuery = entityManager.createQuery("select u from User u where u.email=:email", User.class);
        userTypedQuery.setParameter("email", email);
        try {
            return userTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public long getCountOfEntities() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        query.select(builder.count(query.from(User.class)));
        return entityManager.createQuery(query).getSingleResult();
    }

}
