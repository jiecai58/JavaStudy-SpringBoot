package com.study.restaurant.mcrsrvc.domain.service;

import com.study.restaurant.mcrsrvc.domain.model.Restaurant;
import com.study.restaurant.mcrsrvc.domain.repo.RestaurantRepository;

/**
 * @author Darcy
 *         Created by Darcy on 2017/7/17.
 */
public class RestaurantService extends BaseService<Restaurant, String> {
    private RestaurantRepository<Restaurant, String> restaurantRepository;

    public RestaurantService(RestaurantRepository<Restaurant, String> repository) {
        super(repository);
        this.restaurantRepository = repository;
    }

    public void add(Restaurant restaurant) throws Exception {
        if (restaurantRepository.containsName(restaurant.getName())) {
            throw new Exception(String.format("There is already a product with the name - %s", restaurant.getName()));
        }

        if (restaurant.getName() == null || "".equals(restaurant.getName())) {
            throw new Exception("Restaurant name cannot be null or empty string.");
        }
        super.add(restaurant);
    }
}
