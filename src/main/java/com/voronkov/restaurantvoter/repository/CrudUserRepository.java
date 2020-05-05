package com.voronkov.restaurantvoter.repository;

import com.voronkov.restaurantvoter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudUserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
