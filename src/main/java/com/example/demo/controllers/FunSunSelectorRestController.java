package com.example.demo.controllers;

import com.example.demo.entity.FunSunSearchBySelectorEntity;
import com.example.demo.service.FunSunSelectorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/selector")
public class FunSunSelectorRestController {
    private final FunSunSelectorService funSunSelectorService;

    public FunSunSelectorRestController(FunSunSelectorService funSunSelectorService) {
        this.funSunSelectorService = funSunSelectorService;
    }

    // Save search param
    @PostMapping("/add")
    public String addSearchSelector(@RequestParam String hotel, @RequestParam String price, @RequestParam String selector) {
        funSunSelectorService.saveSelector(new FunSunSearchBySelectorEntity(hotel, price, selector));
        return "done";
    }

    // Update search param
    @PostMapping("/update")
    public String updateSearchSelector(@RequestParam String hotel, @RequestParam String price) {
        funSunSelectorService.saveSelector(new FunSunSearchBySelectorEntity(hotel, price));
        return "done";
    }

    // Get all search param
    @GetMapping("/all")
    public List<FunSunSearchBySelectorEntity> getAllTourLinks() {
        return funSunSelectorService.findAll();
    }

    // Remove ID
    @DeleteMapping("/remove/{id}")
    public String removeLinkById(@PathVariable Long id) {
        try {
            funSunSelectorService.deleteSelector(id);
        } catch (IndexOutOfBoundsException e) {
            return "error";
        }
        return "done";
    }
}
