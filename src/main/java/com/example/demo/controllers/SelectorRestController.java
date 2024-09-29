package com.example.demo.controllers;

import com.example.demo.entity.SelectorEntity;
import com.example.demo.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/selector")
public class SelectorRestController {

    private final BaseService<SelectorEntity, Long> selectorService;

    public SelectorRestController(BaseService<SelectorEntity, Long> selectorService) {
        this.selectorService = selectorService;
    }

    // Получить все селекторы
    @GetMapping
    public List<SelectorEntity> getAllSelectors() {
        return selectorService.findAll();
    }

    // Получить селектор по ID
    @GetMapping("/{id}")
    public ResponseEntity<SelectorEntity> getSelectorById(@PathVariable Long id) {
        return selectorService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Создать новый селектор
    @PostMapping
    public ResponseEntity<SelectorEntity> createSelector(@RequestBody SelectorEntity selector) {
        SelectorEntity savedSelector = selectorService.save(selector);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSelector);
    }

    // Обновить селектор
    @PutMapping("/{id}")
    public ResponseEntity<SelectorEntity> updateSelector(
            @PathVariable Long id, @RequestBody SelectorEntity selectorDetails) {
        return selectorService.findById(id)
                .map(selector -> {
                    selector.setWhichSite(selectorDetails.getWhichSite());
                    selector.setHotelNameSelector(selectorDetails.getHotelNameSelector());
                    selector.setPriceSelector(selectorDetails.getPriceSelector());
                    SelectorEntity updatedSelector = selectorService.update(selector);
                    return ResponseEntity.ok(updatedSelector);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Удалить селектор
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSelector(@PathVariable Long id) {
        if (selectorService.findById(id).isPresent()) {
            selectorService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
