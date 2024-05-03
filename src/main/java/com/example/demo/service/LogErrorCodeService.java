package com.example.demo.service;

import com.example.demo.entity.LogErrorCodeEntity;

import java.util.List;

public interface LogErrorCodeService {

    List<LogErrorCodeEntity> findAll();

    void saveErrorLog(LogErrorCodeEntity logErrorCodeEntity);
}
