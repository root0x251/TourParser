package com.example.demo.service.impl;

import com.example.demo.entity.FunSunSearchBySelectorEntity;
import com.example.demo.repository.FunSunSelectorRepo;
import com.example.demo.service.FunSunSelectorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FunSunSelectorImpl implements FunSunSelectorService {
    private final FunSunSelectorRepo funSunSelectorRepo;

    public FunSunSelectorImpl(FunSunSelectorRepo funSunSelectorRepo) {
        this.funSunSelectorRepo = funSunSelectorRepo;
    }

    @Override
    public void saveSelector(FunSunSearchBySelectorEntity funSunSearchBySelectorEntity) {
        funSunSelectorRepo.save(funSunSearchBySelectorEntity);
    }

    @Override
    public void updateSelector(FunSunSearchBySelectorEntity funSunSearchBySelectorEntity) {
        funSunSelectorRepo.save(funSunSearchBySelectorEntity);
    }

    @Override
    public void deleteSelector(Long id) {
        funSunSelectorRepo.deleteById(id);
    }

    @Override
    public FunSunSearchBySelectorEntity findSelectorById(Long id) {
        return funSunSelectorRepo.findById(id).get();
    }

    @Override
    public List<FunSunSearchBySelectorEntity> findAll() {
        return funSunSelectorRepo.findAll();
    }


}
