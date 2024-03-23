package com.example.demo.service;

import com.example.demo.entity.TourInfoEntity;

import java.util.List;
import java.util.Optional;

public interface TourService {

    List<TourInfoEntity> findAll();

    Optional<TourInfoEntity> findById(Long id);

    void saveTour(TourInfoEntity tourInfoEntity);

    void updateTour(TourInfoEntity tourInfoEntity);

    void deleteHotel(Long id);
}
