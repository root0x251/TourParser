package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
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
    @Column(name = "location", columnDefinition = "TEXT")
    private String location;
    @Column(name = "fly_date", columnDefinition = "TEXT")
    private String flyDate;
    @Column(name = "count_night", columnDefinition = "TEXT")
    private String countNight;
    @Column(name = "rating", columnDefinition = "FLOAT")
    private float rating;
    @Column(name = "count_reviews", columnDefinition = "INTEGER")
    private int countReviews;
    @Column(name = "link", columnDefinition = "TEXT")
    private String currentLinkOnTour;
    @Column(name = "difference_in_price", columnDefinition = "INTEGER")
    private int differenceInPrice;


    public TourInfoEntity() {
    }

    public TourInfoEntity(String hotelName, int tourPrice, String location, String flyDate, String countNight, float rating, int countReviews, String currentLinkOnTour, int differenceInPrice) {
        this.hotelName = hotelName;
        this.tourPrice = tourPrice;
        this.location = location;
        this.flyDate = flyDate;
        this.countNight = countNight;
        this.rating = rating;
        this.countReviews = countReviews;
        this.currentLinkOnTour = currentLinkOnTour;
        this.differenceInPrice = differenceInPrice;
    }
}
