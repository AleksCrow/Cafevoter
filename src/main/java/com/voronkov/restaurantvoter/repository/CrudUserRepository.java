package com.voronkov.restaurantvoter.repository;

import com.voronkov.restaurantvoter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudUserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
