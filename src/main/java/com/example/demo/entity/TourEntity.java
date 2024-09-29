package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tour")
public class TourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "hotel_name", columnDefinition = "TEXT")
    private String hotelName;
    @Column(name = "current_price", columnDefinition = "INTEGER")
    private int currentPrice;
    @Column(name = "price_change", columnDefinition = "INTEGER")
    private String priceChange;

    @OneToOne
    @JoinColumn(name = "link_id", nullable = false)
    private LinkEntity link; // связь с таблицей LinkEntity

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TourPriceHistoryEntity> priceHistory = new ArrayList<>(); // связь с историей цен



}
