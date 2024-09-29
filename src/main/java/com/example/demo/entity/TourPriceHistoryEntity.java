package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tour_price_history")
public class TourPriceHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false)
    private TourEntity tour; // связь с таблицей tour

}
