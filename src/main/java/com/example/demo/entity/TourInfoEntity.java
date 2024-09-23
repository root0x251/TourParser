package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tour_info")
public class TourInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "hotel_name", columnDefinition = "TEXT")
    private String hotelName;
    @Column(name = "price", columnDefinition = "INTEGER")
    private int tourPrice;
    @Column(name = "old_price", columnDefinition = "INTEGER")
    private int oldTourPrice;
    @Column(name = "link", columnDefinition = "TEXT")
    private String currentLinkOnTour;
    @Column(name = "difference_in_price", columnDefinition = "INTEGER")
    private int differenceInPrice;

    public TourInfoEntity(String hotelName, int tourPrice, int oldTourPrice, String currentLinkOnTour, int differenceInPrice) {
        this.hotelName = hotelName;
        this.tourPrice = tourPrice;
        this.oldTourPrice = oldTourPrice;
        this.currentLinkOnTour = currentLinkOnTour;
        this.differenceInPrice = differenceInPrice;
    }
}
