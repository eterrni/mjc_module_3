package com.epam.esm.repository;

import com.epam.esm.entity.User;

import java.util.List;

/**
 * Interface provides methods to interact with User data from database.
 * Methods should connect to database and manipulate with data(read)
 */
public interface IUserRepository {
    /**
     * Connects to database and returns all Users.
     *
     * @return List of all {@link User} entities from database.
     */
    List<User> readAll(int page, int size);

    /**
     * Connects to database and returns User by ID.
     *
     * @param id is User ID value.
     * @return {@link User} the entity on this ID.
     */
    User read(int id);

    /**
     * Get count of exist users.
     *
     * @return number of exist users.
     */
    long getCountOfEntities();

    /**
     * Get User with highest cost of all orders.
     *
     * @return {@link User}
     */
    User getUserWithHighestCostOfAllOrders();
}
