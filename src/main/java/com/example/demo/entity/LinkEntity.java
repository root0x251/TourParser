package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "link")
public class LinkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "link", columnDefinition = "TEXT")
    String link;

    @OneToOne(mappedBy = "link", cascade = CascadeType.ALL)
    private TourEntity tour; // связь с таблицей TourEntity

    @ManyToOne
    @JoinColumn(name = "selector_id", nullable = false)
    private SelectorEntity selector; // связь с таблицей SelectorEntity

    public LinkEntity(String link, SelectorEntity selector) {
        this.link = link;
        this.selector = selector;
    }
}
