package com.example.demo.repository;

import com.example.demo.entity.TourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<TourEntity, Long> {

    @Query(value = "SELECT * FROM public.tour_info WHERE hotel_name = :hotelName", nativeQuery = true)
    TourEntity findByHotelName(String hotelName);

}
