package com.epam.esm.repository.user;

import com.epam.esm.entity.User;
import com.epam.esm.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> readAll() {
        return entityManager.createQuery("select user from User user", User.class).getResultList();
    }

    @Override
    public User read(int id) {
        return entityManager.find(User.class, id);
    }
}
