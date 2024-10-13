package com.example.demo.repository;

import com.example.demo.entity.LinkEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends CrudRepository<LinkEntity, Long> {
}
