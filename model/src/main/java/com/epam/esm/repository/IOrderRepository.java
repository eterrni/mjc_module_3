package com.epam.esm.repository;

import com.epam.esm.entity.Order;

import java.util.List;

public interface IOrderRepository {
    /**
     * Connects to database and returns all Orders.
     *
     * @return List of found entities.
     */
    List<Order> readAll(int offset, int limit);

    /**
     * Connects to database and returns Order by ID.
     *
     * @param id is Order ID value.
     * @return Entity from database.
     */
    Order read(int id);

    /**
     * Connects to database and returns all Orders.
     *
     * @param userID is User ID value.
     * @return List of found entities.
     */
    List<Order> readOrdersByUserID(int userID);

    /**
     * Create a new entity.
     *
     * @param order Entity with data for creating Order.
     * @return Created entity from database
     */
    Order create(Order order);

    /**
     * Get count of exist orders.
     *
     * @return number of exist orders.
     */
    long getCountOfEntities();
}
