package com.example.demo.service.impl;

import com.example.demo.entity.SelectorEntity;
import com.example.demo.repository.SelectorRepository;
import com.example.demo.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SelectorImpl implements BaseService<SelectorEntity, Long> {
    private final SelectorRepository selectorRepository;

    public SelectorImpl(SelectorRepository selectorRepository) {
        this.selectorRepository = selectorRepository;
    }

    @Override
    public List<SelectorEntity> findAll() {
        return selectorRepository.findAll();
    }

    @Override
    public Optional<SelectorEntity> findById(Long aLong) {
        return selectorRepository.findById(aLong);
    }

    @Override
    public SelectorEntity save(SelectorEntity entity) {
        return selectorRepository.save(entity);
    }

    @Override
    public SelectorEntity update(SelectorEntity entity) {
        return selectorRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        selectorRepository.deleteById(aLong);
    }
}
