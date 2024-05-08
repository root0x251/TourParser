package com.example.demo.repository;

import com.example.demo.entity.TourInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TourInfoRepo extends JpaRepository<TourInfoEntity, Long> {

    @Query(value = "SELECT * FROM public.tour_info WHERE hotel_name = :hotelName", nativeQuery = true)
    TourInfoEntity findByHotelName(String hotelName);

    @Query(value = "SELECT * FROM public.tour_info WHERE link = :currentLinkOnTour", nativeQuery = true)
    TourInfoEntity findByURL(String currentLinkOnTour);


    @Modifying
    @Query(value = "UPDATE public.tour_info SET difference_in_price = :differenceInPrice WHERE link = :currentLinkOnTour", nativeQuery = true)
    void updatePrice(int differenceInPrice, String currentLinkOnTour);

}
