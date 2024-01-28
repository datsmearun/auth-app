package com.arun.restaurant.repository;

import com.arun.restaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepo extends JpaRepository<Menu, Long> {

    Menu findByRestaurantId(Long restaurantId);
}
