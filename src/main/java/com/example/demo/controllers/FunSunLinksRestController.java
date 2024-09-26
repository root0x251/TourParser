package com.example.demo.controllers;

import com.example.demo.entity.ParseLinkEntity;
import com.example.demo.service.ParseLinkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/link")
public class FunSunLinksRestController {

    private final ParseLinkService parseLinkService;
    public FunSunLinksRestController(ParseLinkService parseLinkService) {
        this.parseLinkService = parseLinkService;
    }

    // get all tours
    @GetMapping("/all")
    public List<ParseLinkEntity> findAll() {
        return parseLinkService.findAll();
    }

    // add link to DB
    @PostMapping("/add")
    public String setTourLink(@RequestBody String link) {
        parseLinkService.saveLink(new ParseLinkEntity(link.replace("\"", "")));
        return "done";
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
