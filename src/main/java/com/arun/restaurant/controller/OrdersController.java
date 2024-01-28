package com.arun.restaurant.controller;

import com.arun.restaurant.entity.OrderItems;
import com.arun.restaurant.entity.Orders;
import com.arun.restaurant.repository.OrderItemsRepo;
import com.arun.restaurant.repository.OrdersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    OrdersRepo orderRepository;

    @Autowired
    OrderItemsRepo orderItemRepository;

    @GetMapping
    @RequestMapping("/{restaurantId}/list")
    // manager can access (suresh)
    public List<Orders> getOrders(@PathVariable Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    @GetMapping
    @RequestMapping("/{orderId}")
    // manager can access (suresh)
    public Orders getOrderDetails(@PathVariable Long orderId) {
        Orders order = orderRepository.findById(orderId).get();
        order.setOrderItems(orderItemRepository.findByOrderId(order.getId()));
        return order;
    }

    @PostMapping
    // authenticated users can access
    public Orders createOrder(Orders order) {
        orderRepository.save(order);
        List<OrderItems> orderItems = order.getOrderItems();
        orderItems.forEach(orderItem -> {
            orderItem.setOrderId(order.id);
            orderItemRepository.save(orderItem);
        });
        return order;
    }
}
