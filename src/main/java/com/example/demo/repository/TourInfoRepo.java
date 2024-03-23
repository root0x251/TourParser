package com.example.demo.repository;

import com.example.demo.entity.TourInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TourInfoRepo extends JpaRepository<TourInfoEntity, Long> {

    @Query(value = "SELECT * FROM public.tour_info WHERE hotel_name = :hotelName", nativeQuery = true)
    TourInfoEntity findByHotelName(String hotelName);

}
