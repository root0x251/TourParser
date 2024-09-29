package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "selector")
public class SelectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    @Column(name = "hotel_selector", columnDefinition = "TEXT")
    String hotelNameSelector;
    @Column(name = "price_selector", columnDefinition = "TEXT")
    String priceSelector;
    @Column(name = "which_site", columnDefinition = "TEXT")
    String whichSite;

    @OneToMany(mappedBy = "selector", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LinkEntity> links = new ArrayList<>();

    public SelectorEntity(String hotelNameSelector, String priceSelector, String whichSite) {
        this.hotelNameSelector = hotelNameSelector;
        this.priceSelector = priceSelector;
        this.whichSite = whichSite;
    }
}
