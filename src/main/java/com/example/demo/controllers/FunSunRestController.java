package com.example.demo.controllers;

import com.example.demo.entity.TourInfoEntity;
import com.example.demo.service.TourService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FunSunRestController {

    public static ArrayList<String> allLinks = new ArrayList<>();

    private final TourService tourService;

    public FunSunRestController(TourService tourService) {
        this.tourService = tourService;
    }


    // get all tours
    @GetMapping("/all")
    public List<TourInfoEntity> findAll() {
        return tourService.findAll();
    }

    @PostMapping("/add")
    public String setTourLink(@RequestBody String link) {
        String result = link.replace("\"", "");
        allLinks.add(result);
        return "done";
    }

    @GetMapping("/allLinksFromArray")
    public ArrayList<String> setTourLink() {
        return allLinks;
    }

    @DeleteMapping("/remove/{id}")
    public String removeFromArray(@PathVariable int id) {
        try {
            allLinks.remove(id);
        } catch (IndexOutOfBoundsException e) {
            return "error";
        }
        return "done";
    }
}
