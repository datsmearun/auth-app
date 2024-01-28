package com.arun.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@Table(name = "order_items")
public class OrderItems {

    @Id
    @GeneratedValue
    public Long id;

    private Long orderId;

    private Long menuItemId;

    private BigDecimal price;
}
