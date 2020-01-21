package com.voronkov.restaurantvoter.service;

import com.voronkov.restaurantvoter.model.Meal;
import com.voronkov.restaurantvoter.model.Restaurant;
import com.voronkov.restaurantvoter.model.User;
import com.voronkov.restaurantvoter.repository.CrudRestaurantRepository;
import com.voronkov.restaurantvoter.utils.exception.DontCanVoteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.voronkov.restaurantvoter.utils.TimeUtil.canVote;

@Service
public class RestaurantService {

    private final CrudRestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(CrudRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    public Restaurant getById(int id) {
        return find(id);
    }

    public List<Meal> getMeals(int id) {
        return find(id).getMeals();
    }

    public Restaurant save(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }

    public void delete(int id) {
        Restaurant restaurant = find(id);
        restaurantRepository.delete(restaurant);
    }

    public void vote(User user, int restaurantId) {
        Restaurant restaurant = find(restaurantId);
        Set<User> votes = restaurant.getVotes();
        if (!canVote(restaurant)) {
            throw new DontCanVoteException();
        }
        if (votes.contains(user)) {
            votes.remove(user);
        } else {
            votes.add(user);
        }
        restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getByDateOrBetweenDateTimes(@Nullable LocalDate startDateTime, @Nullable LocalDate endDateTime) {
        return restaurantRepository.getRestaurantByCreatedDateBetween(startDateTime, endDateTime);
    }

    private Restaurant find(int id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
    }
}
