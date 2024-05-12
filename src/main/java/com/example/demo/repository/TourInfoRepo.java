package com.example.demo.repository;

import com.example.demo.entity.TourInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TourInfoRepo extends JpaRepository<TourInfoEntity, Long> {


    @Query(value = "SELECT * FROM public.tour_info WHERE link = :currentLinkOnTour", nativeQuery = true)
    TourInfoEntity findByURL(String currentLinkOnTour);

    @Transactional
    @Modifying
    @Query(value = "UPDATE public.tour_info SET price = :price, difference_in_price = :differenceInPrice WHERE link = :currentLinkOnTour", nativeQuery = true)
    void updateHotelPrice(int price, int differenceInPrice, String currentLinkOnTour);

//    @Query (value = "SELECT id, count_night, count_reviews, link, difference_in_price, fly_date, hotel_name, location, rating, price FROM public.tour_info where hotel_name = :hotelName", nativeQuery = true)

    @Query (value = "SELECT * FROM public.tour_info where hotel_name = :hotelName", nativeQuery = true)
    List<TourInfoEntity> allPriceForHotelName (String hotelName);


}
