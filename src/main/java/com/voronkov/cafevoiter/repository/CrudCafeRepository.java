package com.voronkov.cafevoiter.repository;

import com.voronkov.cafevoiter.model.Cafe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CrudCafeRepository extends JpaRepository<Cafe, Integer> {

    List<Cafe> getCafeByCreatedDateBetween(LocalDate startDate, LocalDate endDate);
}
