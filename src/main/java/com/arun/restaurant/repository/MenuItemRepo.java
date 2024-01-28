package com.arun.restaurant.repository;

import com.arun.restaurant.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findAllByMenuId(Long id);

}
