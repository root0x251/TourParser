package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "fun_sun_selector")
public class FunSunSearchBySelectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "hotel_selector", columnDefinition = "TEXT")
    String hotelName;
    @Column(name = "price_selector", columnDefinition = "TEXT")
    String price;
    @Column(name = "for_which_site", columnDefinition = "TEXT")
    String forWhichSite;

    public FunSunSearchBySelectorEntity(String hotelName, String price) {
        this.hotelName = hotelName;
        this.price = price;
    }

    public FunSunSearchBySelectorEntity(String hotelName, String price, String forWhichSite) {
        this.hotelName = hotelName;
        this.price = price;
        this.forWhichSite = forWhichSite;
    }
}
