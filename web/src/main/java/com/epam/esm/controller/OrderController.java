package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.order.OrderService;
import com.epam.esm.util.CreateParameterOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller that handles requests related to the order
 *
 * @author Alexander Novikov
 */
@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Invokes service method to get List of all Orders.
     *
     * @return List of {@link OrderDto} objects with Order data.
     */
    @GetMapping("/orders")
    public List<OrderDto> readAll() {
        return orderService.readAll();
    }

    /**
     * Invokes service method to get List of all Orders that matches parameter userID
     *
     * @param userID user id we want to view the list of his orders
     * @return List of {@link OrderDto} objects with Order data.
     */
    @GetMapping(value = "/orders", params = "user")
    public List<OrderDto> readByUserId(@RequestParam(value = "user") int userID) {
        return orderService.readOrdersByUserID(userID);
    }

    /**
     * Invokes service method to get Order that matches parameter orderID
     *
     * @param orderID The order number that we want to receive.
     * @return Object with Order data.
     */
    @GetMapping("/order/{orderID}")
    public OrderDto read(@PathVariable int orderID) {
        return orderService.read(orderID);
    }

    /**
     * Invokes service method to create Order.
     *
     * @param createParameterOrder Object containing data for creating an order.
     * @return Created Order.
     */
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody CreateParameterOrder createParameterOrder) {
        return orderService.create(createParameterOrder);
    }
}