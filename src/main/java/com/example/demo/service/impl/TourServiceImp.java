package com.example.demo.service.impl;

import com.example.demo.entity.TourInfoEntity;
import com.example.demo.repository.TourInfoRepo;
import com.example.demo.service.TourService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TourServiceImp implements TourService {
    private final TourInfoRepo tourInfoRepo;

    public TourServiceImp(TourInfoRepo tourInfoRepo) {
        this.tourInfoRepo = tourInfoRepo;
    }

    @Override
    public List<TourInfoEntity> findAll() {
        return tourInfoRepo.findAll();
    }

    @Override
    public Optional<TourInfoEntity> findById(Long id) {
        return tourInfoRepo.findById(id);
    }

    @Override
    public void saveTour(TourInfoEntity tourInfoEntity) {
        tourInfoRepo.save(tourInfoEntity);
    }

    @Override
    public void updateTour(TourInfoEntity tourInfoEntity) {
        tourInfoRepo.save(tourInfoEntity);
    }

    @Override
    public void deleteHotel(Long id) {
        tourInfoRepo.deleteById(id);
    }
}
