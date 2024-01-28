package com.arun.restaurant.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private Long menuId;

    private String name;

    private String description;

    private String type;

    @Column(name = "group_name")
    private String group;

    private BigDecimal price;
}
