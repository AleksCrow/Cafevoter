package com.voronkov.cafevoiter.model;

import javax.persistence.Embeddable;

@Embeddable
public class Meal {

    private String name;

    private Double price;

    public Meal() {
    }

    public Meal(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Meals{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
