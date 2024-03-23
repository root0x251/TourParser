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
    @Column(name = "about_hotel", columnDefinition = "TEXT")
    private String aboutHotel;
    @Column(name = "more_about_hotel", columnDefinition = "TEXT")
    private String aboutHotelMore;
    @Column(name = "beach_info", columnDefinition = "TEXT")
    private String beachInfo;
    @Column(name = "reviews", columnDefinition = "FLOAT")
    private float reviews;
    @Column(name = "count_reviews", columnDefinition = "INTEGER")
    private int countReviews;
    @Column(name = "link", columnDefinition = "TEXT")
    private String currentLinkOnTour;
    @Column(name = "difference_in_price", columnDefinition = "INTEGER")
    private int differenceInPrice;

    public TourInfoEntity() {
    }

    public TourInfoEntity(String hotelName, int tourPrice, String aboutHotel, String aboutHotelMore, String beachInfo, float reviews, int countReviews, String currentLinkOnTour, int differenceInPrice) {
        this.hotelName = hotelName;
        this.tourPrice = tourPrice;
        this.aboutHotel = aboutHotel;
        this.aboutHotelMore = aboutHotelMore;
        this.beachInfo = beachInfo;
        this.reviews = reviews;
        this.countReviews = countReviews;
        this.currentLinkOnTour = currentLinkOnTour;
        this.differenceInPrice = differenceInPrice;
    }

}
