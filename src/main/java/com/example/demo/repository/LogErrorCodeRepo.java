package com.example.demo.repository;

import com.example.demo.entity.LogErrorCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogErrorCodeRepo extends JpaRepository<LogErrorCodeEntity, Long> {
}
