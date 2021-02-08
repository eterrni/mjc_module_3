package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.util.CreateParameterOrder;

import java.util.List;

/**
 * Interface provides methods to interact with OrderRepository.
 *
 * @author Alexander Novikov
 */
public interface IOrderService {
    /**
     * Invokes DAO method to get List of all Orders from database.
     *
     * @return Objects with order data.
     */
    List<OrderDto> readAll(int page, int size);

    /**
     * Invokes DAO method to get List of all Orders from database.
     *
     * @param id order id
     * @return Object with order data.
     */
    OrderDto read(int id);

    /**
     * Invokes the DAO method to get a list of all orders by User ID from the database.
     *
     * @param userID is User ID value.
     * @return List of all orders, with the corresponding user ID.
     */
    List<OrderDto> readOrdersByUserID(int userID);

    /**
     * Create a new Order.
     *
     * @param createParameterOrder Data for order creation (number of a user, the certificate numbers)
     * @return Object with order data.
     */
    OrderDto create(CreateParameterOrder createParameterOrder);

}
