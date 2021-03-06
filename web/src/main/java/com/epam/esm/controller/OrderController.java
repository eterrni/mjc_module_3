package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.order.OrderService;
import com.epam.esm.util.CreateParameterOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

/**
 * Controller that handles requests related to the order
 *
 * @author Alexander Novikov
 */
@RestController
@RequestMapping("/api")
public class OrderController extends HATEOASController<OrderDto> {

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
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<OrderDto> readAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "4") int size) {
        List<OrderDto> orderDtoList = orderService.readAll(page, size);
        addLinksToListOrder(orderDtoList);
        return addPagination(orderDtoList, page, size, orderService.getCountOfEntities());
    }

    /**
     * Invokes service method to get List of all Orders that matches parameter userID
     *
     * @param userID user id we want to view the list of his orders
     * @return List of {@link OrderDto} objects with Order data.
     */
    @GetMapping(value = "/orders", params = "user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<OrderDto> readByUserId(@RequestParam(value = "user") int userID) {
        List<OrderDto> orderDtoList = orderService.readOrdersByUserID(userID);
        addLinksToListOrder(orderDtoList);
        return orderDtoList;
    }

    /**
     * Invokes service method to get Order that matches parameter orderID
     *
     * @param orderID The order number that we want to receive.
     * @return Object with Order data.
     */
    @GetMapping("/order/{orderID}")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderDto read(@PathVariable int orderID) {
        return addLinksToOrder(orderService.read(orderID));
    }

    /**
     * Invokes service method to create Order.
     *
     * @param createParameterOrder Object containing data for creating an order.
     * @return Created Order.
     */
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public OrderDto create(@RequestBody CreateParameterOrder createParameterOrder) {
        return orderService.create(createParameterOrder);
    }

    /**
     * Delete order by ID
     *
     * @param id of the order we want to delete
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/order/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> removeOrder(@PathVariable int id) {
        orderService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
