package com.arun.restaurant.repository;

import com.arun.restaurant.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepo extends JpaRepository<OrderItems, Long> {

    List<OrderItems> findByOrderId(Long id);
}
