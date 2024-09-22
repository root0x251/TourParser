package com.example.demo.controllers;

import com.example.demo.entity.ParseLinkEntity;
import com.example.demo.entity.TourInfoEntity;
import com.example.demo.service.ParseLinkService;
import com.example.demo.service.TourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FunSunRestController {
    private final TourService tourService;
    private final ParseLinkService parseLinkService;

    public FunSunRestController(TourService tourService, ParseLinkService parseLinkService) {
        this.tourService = tourService;
        this.parseLinkService = parseLinkService;
    }

    // get all tours
    @GetMapping("/all")
    public List<TourInfoEntity> findAll() {
        return tourService.findAll();
    }

    // add link to DB
    @PostMapping("/add")
    public String setTourLink(@RequestBody String link) {
        parseLinkService.saveLink(new ParseLinkEntity(link.replace("\"", "")));
        return "done";
    }

    // Get all Links
    @GetMapping("/allLinksFromArray")
    public List<ParseLinkEntity> getAllTourLinks() {
        return parseLinkService.findAll();
    }

    // Remove link By ID
    @DeleteMapping("/remove/{id}")
    public String removeLinkById(@PathVariable Long id) {
        try {
            parseLinkService.deleteLinkById(id);
        } catch (IndexOutOfBoundsException e) {
            return "error";
        }
        return "done";
    }
}
