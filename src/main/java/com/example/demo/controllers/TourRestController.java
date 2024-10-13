package com.example.demo.controllers;

import com.example.demo.entity.TourEntity;
import com.example.demo.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tour")
public class TourRestController {

    private final TourRepository tourRepository;

    @Autowired
    public TourRestController(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @GetMapping("/allTour")
    public List<TourEntity> allTour() {
        for (TourEntity tourEntity : tourRepository.findAll()) {
            System.out.println(tourEntity);
        }
        return (List<TourEntity>) tourRepository.findAll();
    }

}
