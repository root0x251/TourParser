package com.example.demo.repository;

import com.example.demo.entity.FunSunSearchBySelectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FunSunSelectorRepo extends JpaRepository<FunSunSearchBySelectorEntity, Long> {

    @Query(value = "SELECT * FROM public.fun_sun_selector WHERE for_which_site = :selector", nativeQuery = true)
    FunSunSearchBySelectorEntity findByForWhichSite(String selector);

}
