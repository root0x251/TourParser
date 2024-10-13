package com.example.demo.service.impl;

import com.example.demo.entity.SelectorEntity;
import com.example.demo.repository.SelectorRepo;
import com.example.demo.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SelectorImpl implements BaseService<SelectorEntity, Long> {

    private final SelectorRepo selectorRepo;

    public SelectorImpl(SelectorRepo linkRepository) {
        this.selectorRepo = linkRepository;
    }

    @Override
    public List<SelectorEntity> findAll() {
        return (List<SelectorEntity>) selectorRepo.findAll();
    }

    @Override
    public Optional<SelectorEntity> findById(Long aLong) {
        return selectorRepo.findById(aLong);
    }

    @Override
    public SelectorEntity save(SelectorEntity entity) {
        return selectorRepo.save(entity);
    }

    @Override
    public SelectorEntity update(SelectorEntity entity) {
        return selectorRepo.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        selectorRepo.deleteById(aLong);
    }
}
