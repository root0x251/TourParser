package com.example.demo.service.impl;

import com.example.demo.entity.TourPriceHistoryEntity;
import com.example.demo.repository.TourPriseHistoryRepository;
import com.example.demo.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourPriceHistoryImpl implements BaseService<TourPriceHistoryEntity, Long> {

    private final TourPriseHistoryRepository tourPriseHistoryRepository;

    public TourPriceHistoryImpl(TourPriseHistoryRepository tourPriseHistoryRepository) {
        this.tourPriseHistoryRepository = tourPriseHistoryRepository;
    }

    @Override
    public List<TourPriceHistoryEntity> findAll() {
        return tourPriseHistoryRepository.findAll();
    }

    @Override
    public Optional<TourPriceHistoryEntity> findById(Long aLong) {
        return tourPriseHistoryRepository.findById(aLong);
    }

    @Override
    public TourPriceHistoryEntity save(TourPriceHistoryEntity entity) {
        return tourPriseHistoryRepository.save(entity);
    }

    @Override
    public TourPriceHistoryEntity update(TourPriceHistoryEntity entity) {
        return tourPriseHistoryRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        tourPriseHistoryRepository.deleteById(aLong);
    }
}
