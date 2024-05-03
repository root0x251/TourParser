package com.example.demo.controllers;

import com.example.demo.entity.LogErrorCodeEntity;
import com.example.demo.entity.TourInfoEntity;
import com.example.demo.service.LogErrorCodeService;
import com.example.demo.service.TourService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class FunSunRestController {

    public static ArrayList<String> parsLinks = new ArrayList<>();

    private final TourService tourService;
    private final LogErrorCodeService logErrorCodeService;

    public FunSunRestController(TourService tourService, LogErrorCodeService logErrorCodeService) {
        this.tourService = tourService;
        this.logErrorCodeService = logErrorCodeService;
    }


    // get all error
    @GetMapping("/err")
    public List<LogErrorCodeEntity> allError() {
        return logErrorCodeService.findAll();
    }

    // get all tours
    @GetMapping("/all")
    public List<TourInfoEntity> findAll() {
        return tourService.findAll();
    }

    @PostMapping("/add")
    public String setTourLink(@RequestBody String link) {
        String result = link.replace("\"", "");
        parsLinks.add(result);
        return "done";
    }

    @GetMapping("/allLinksFromArray")
    public ArrayList<String> setTourLink() {
        return parsLinks;
    }

    @DeleteMapping("/remove/{id}")
    public String removeFromArray(@PathVariable int id) {
        try {
            parsLinks.remove(id);
        } catch (IndexOutOfBoundsException e) {
            return "error";
        }
        return "done";
    }
}
