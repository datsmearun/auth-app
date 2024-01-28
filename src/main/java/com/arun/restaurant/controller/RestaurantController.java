package com.arun.restaurant.controller;

import com.arun.restaurant.entity.Menu;
import com.arun.restaurant.entity.MenuItem;
import com.arun.restaurant.entity.Restaurant;
import com.arun.restaurant.repository.MenuItemRepo;
import com.arun.restaurant.repository.MenuRepo;
import com.arun.restaurant.repository.RestaurantRepo;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
@SecurityRequirement(name = "Keycloak")
public class RestaurantController {

    @Autowired
    RestaurantRepo restaurantRepository;

    @Autowired
    MenuRepo menuRepository;

    @Autowired
    MenuItemRepo menuItemRepository;

    @GetMapping
    @RequestMapping("/public/list")
    //Public API
    public List<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping
    @RequestMapping("/public/menu/{restaurantId}")
    //Public API
    public Menu getMenu(@PathVariable Long restaurantId) {
        Menu menu = menuRepository.findByRestaurantId(restaurantId);
        menu.setMenuItems(menuItemRepository.findAllByMenuId(menu.id));
        return menu;
    }

    @PostMapping
    // admin can access
    @PreAuthorize("hasRole('Administrator')")
    public Restaurant createRestaurant(@RequestBody Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @PostMapping
    @RequestMapping("/menu")
    // manager can access
    @PreAuthorize("hasRole('manager')")
    public Menu createMenu(@RequestBody Menu menu) {
        menuRepository.save(menu);
        menu.getMenuItems().forEach(menuItem -> {
            menuItem.setMenuId(menu.id);
            menuItemRepository.save(menuItem);
        });
        return menu;
    }

    @PutMapping
    @RequestMapping("/menu/item/{itemId}/{price}")
    // owner can access
    @PreAuthorize("hasRole('Owner')")
    public MenuItem updateMenuItemPrice(@PathVariable Long itemId
            , @PathVariable BigDecimal price) {
        Optional<MenuItem> menuItem = menuItemRepository.findById(itemId);
        menuItem.get().setPrice(price);
        menuItemRepository.save(menuItem.get());
        return menuItem.get();
    }
}
