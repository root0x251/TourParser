package com.example.demo.controllers;

import com.example.demo.entity.LinkEntity;
import com.example.demo.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("link")
public class LinkRestController {


    private final BaseService<LinkEntity, Long> linkService;

    public LinkRestController(BaseService<LinkEntity, Long> linkService) {
        this.linkService = linkService;
    }

    // Получить все ссылки
    @GetMapping
    public List<LinkEntity> getAllLinks() {
        return linkService.findAll();
    }

    // Получить ссылку по ID
    @GetMapping("/{id}")
    public ResponseEntity<LinkEntity> getLinkById(@PathVariable Long id) {
        return linkService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Создать новую ссылку
    @PostMapping("/add")
    public ResponseEntity<LinkEntity> createLink(@RequestBody LinkEntity link) {
        LinkEntity savedLink = linkService.save(link);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLink);
    }

    // Обновить ссылку
    @PutMapping("/{id}")
    public ResponseEntity<LinkEntity> updateLink(
            @PathVariable Long id, @RequestBody LinkEntity linkDetails) {
        return linkService.findById(id)
                .map(link -> {
                    link.setLink(linkDetails.getLink());
                    link.setSelector(linkDetails.getSelector());
                    LinkEntity updatedLink = linkService.update(link);
                    return ResponseEntity.ok(updatedLink);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Удалить ссылку
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        if (linkService.findById(id).isPresent()) {
            linkService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
