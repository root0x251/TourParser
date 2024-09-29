package com.example.demo.service.impl;

import com.example.demo.entity.TourEntity;
import com.example.demo.repository.TourRepository;
import com.example.demo.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourImpl implements BaseService<TourEntity, Long> {

    private final TourRepository tourRepository;

    public TourImpl(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public List<TourEntity> findAll() {
        return tourRepository.findAll();
    }

    @Override
    public Optional<TourEntity> findById(Long aLong) {
        return tourRepository.findById(aLong);
    }

    @Override
    public TourEntity save(TourEntity entity) {
        return tourRepository.save(entity);
    }

    @Override
    public TourEntity update(TourEntity entity) {
        return tourRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        tourRepository.deleteById(aLong);
    }
}
