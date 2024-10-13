package com.example.demo.repository;

import com.example.demo.entity.TourPriceHistoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourPriseHistoryRepository extends CrudRepository<TourPriceHistoryEntity, Long> {
}
