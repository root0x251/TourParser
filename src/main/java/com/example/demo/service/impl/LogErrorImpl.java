package com.example.demo.service.impl;

import com.example.demo.entity.LogErrorEntity;
import com.example.demo.repository.LogErrorRepo;
import com.example.demo.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogErrorImpl implements BaseService<LogErrorEntity, Long> {

    private final LogErrorRepo logErrorRepo;

    public LogErrorImpl(LogErrorRepo logErrorRepo) {
        this.logErrorRepo = logErrorRepo;
    }

    @Override
    public List<LogErrorEntity> findAll() {
        return (List<LogErrorEntity>) logErrorRepo.findAll();
    }

    @Override
    public Optional<LogErrorEntity> findById(Long aLong) {
        return logErrorRepo.findById(aLong);
    }

    @Override
    public LogErrorEntity save(LogErrorEntity entity) {
        return logErrorRepo.save(entity);
    }

    @Override
    public LogErrorEntity update(LogErrorEntity entity) {
        return logErrorRepo.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        logErrorRepo.deleteById(aLong);
    }
}
