package com.example.demo.service.impl;

import com.example.demo.entity.LinkEntity;
import com.example.demo.repository.LinkRepository;
import com.example.demo.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkImpl implements BaseService<LinkEntity, Long> {

    private final LinkRepository linkRepository;

    public LinkImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public List<LinkEntity> findAll() {
        return linkRepository.findAll();
    }

    @Override
    public Optional<LinkEntity> findById(Long aLong) {
        return linkRepository.findById(aLong);
    }

    @Override
    public LinkEntity save(LinkEntity entity) {
        return linkRepository.save(entity);
    }

    @Override
    public LinkEntity update(LinkEntity entity) {
        return linkRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        linkRepository.deleteById(aLong);
    }
}
