package com.example.demo.controllers;

import com.example.demo.entity.TourInfoEntity;
import com.example.demo.service.TourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FunSunRestController {

    private final TourService tourService;

    public FunSunRestController() {
        tourService = null;
    }


    // get all tours
    @GetMapping("/all")
    public List<TourInfoEntity> findAll() {
        assert tourService != null;
        return tourService.findAll();
    }

    @PostMapping("/add")
    public String setTourLink(@RequestBody String link) {

        return link;
    }
}
