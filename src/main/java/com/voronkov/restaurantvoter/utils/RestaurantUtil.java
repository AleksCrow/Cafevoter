package com.voronkov.restaurantvoter.utils;

import com.voronkov.restaurantvoter.model.Restaurant;
import com.voronkov.restaurantvoter.to.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {

    private RestaurantUtil() {
    }

    public static List<RestaurantTo> getRestaurantWithVotes(Collection<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantUtil::createWithVote)
                .collect(Collectors.toList());
    }

    public static RestaurantTo createWithVote(Restaurant restaurant) {
        return new RestaurantTo(restaurant, restaurant.getVotes().size());
    }
}
