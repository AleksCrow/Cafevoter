package com.voronkov.restaurantvoter.repository;

import com.voronkov.restaurantvoter.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    List<Restaurant> getRestaurantByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
