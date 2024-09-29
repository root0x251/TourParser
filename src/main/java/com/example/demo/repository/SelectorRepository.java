package com.example.demo.repository;

import com.example.demo.entity.SelectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectorRepository extends JpaRepository<SelectorEntity, Long> {

}
