package com.example.demo.service;

import com.example.demo.entity.FunSunSearchBySelectorEntity;

import java.util.List;

public interface FunSunSelectorService {

    void saveSelector(FunSunSearchBySelectorEntity funSunSearchBySelectorEntity);

    void updateSelector(FunSunSearchBySelectorEntity funSunSearchBySelectorEntity);

    void deleteSelector(Long id);

    FunSunSearchBySelectorEntity findSelectorById(Long id);

    List<FunSunSearchBySelectorEntity> findAll();

}
