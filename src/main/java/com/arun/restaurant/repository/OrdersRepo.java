package com.arun.restaurant.repository;

import com.arun.restaurant.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepo extends JpaRepository<Orders, Long> {

    List<Orders> findByRestaurantId(Long restaurantId);
}
