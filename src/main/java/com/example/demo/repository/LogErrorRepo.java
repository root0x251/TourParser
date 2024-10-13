package com.example.demo.repository;

import com.example.demo.entity.LogErrorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogErrorRepo extends CrudRepository<LogErrorEntity, Long> {
}
