package com.example.demo.controllers;

import com.example.demo.entity.ParseLinkEntity;
import com.example.demo.entity.TourInfoEntity;
import com.example.demo.service.FunSunSelectorService;
import com.example.demo.service.ParseLinkService;
import com.example.demo.service.TourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour")
public class FunSunTourRestController {
    private final TourService tourService;
    private final ParseLinkService parseLinkService;
    private final FunSunSelectorService funSunSelectorService;

    public FunSunTourRestController(TourService tourService, ParseLinkService parseLinkService, FunSunSelectorService funSunSelectorService) {
        this.tourService = tourService;
        this.parseLinkService = parseLinkService;
        this.funSunSelectorService = funSunSelectorService;
    }

    // get all tours
    @GetMapping("/allTour")
    public List<TourInfoEntity> findAll() {
        return tourService.findAll();
    }

    // add link to DB
    @PostMapping("/addTour")
    public String setTourLink(@RequestBody String link) {
        parseLinkService.saveLink(new ParseLinkEntity(link.replace("\"", "")));
        return "done";
    }

    // Get all Links
    @GetMapping("/allLinkForParse")
    public List<ParseLinkEntity> getAllTourLinks() {
        return parseLinkService.findAll();
    }

    // Remove link By ID
    @DeleteMapping("/removeLinkForParse/{id}")
    public String removeLinkById(@PathVariable Long id) {
        try {
            parseLinkService.deleteLinkById(id);
        } catch (IndexOutOfBoundsException e) {
            return "error";
        }
        return "done";
    }
}
