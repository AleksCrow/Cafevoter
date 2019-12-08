package com.voronkov.cafevoiter.repository;

import com.voronkov.cafevoiter.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrudCafeRepository extends JpaRepository<Cafe, Integer> {

}
