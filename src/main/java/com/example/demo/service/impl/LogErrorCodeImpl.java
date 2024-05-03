package com.example.demo.service.impl;

import com.example.demo.entity.LogErrorCodeEntity;
import com.example.demo.repository.LogErrorCodeRepo;
import com.example.demo.service.LogErrorCodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogErrorCodeImpl implements LogErrorCodeService {

    private final LogErrorCodeRepo logErrorCodeRepo;

    public LogErrorCodeImpl(LogErrorCodeRepo logErrorCodeRepo) {
        this.logErrorCodeRepo = logErrorCodeRepo;
    }

    @Override
    public List<LogErrorCodeEntity> findAll() {
        return logErrorCodeRepo.findAll();
    }

    @Override
    public void saveErrorLog(LogErrorCodeEntity logErrorCodeEntity) {
        logErrorCodeRepo.save(logErrorCodeEntity);
    }


}
