package com.voronkov.restaurantvoter.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.voronkov.restaurantvoter.model.Restaurant;

import java.time.LocalDate;
import java.util.Objects;

public class RestaurantTo {

    private Integer id;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdDate;
    private Integer votes;

    public RestaurantTo() {
    }

    public RestaurantTo(Restaurant restaurant, Integer votes) {
        this.id = restaurant.getId();
        this.name = restaurant.getName();
        this.createdDate = restaurant.getCreatedDate();
        this.votes = votes;
    }

    public RestaurantTo(Integer id, String name, LocalDate createdDate, Integer votes) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.votes = votes;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Integer getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "CafeTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", votes=" + votes +
                "}";
    }

    //без equals не проходит валидацию тест CafeRestController.getAllCafes()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestaurantTo)) return false;
        RestaurantTo restaurantTo = (RestaurantTo) o;
        return Objects.equals(id, restaurantTo.id) &&
                Objects.equals(name, restaurantTo.name) &&
                Objects.equals(createdDate, restaurantTo.createdDate) &&
                Objects.equals(votes, restaurantTo.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdDate, votes);
    }
}
