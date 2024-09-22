package com.example.demo.service.impl;

import com.example.demo.entity.ParseLinkEntity;
import com.example.demo.repository.ParseLinkRepos;
import com.example.demo.service.ParseLinkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParseLinkImpl implements ParseLinkService {
    private final ParseLinkRepos parseLinkRepos;

    public ParseLinkImpl(ParseLinkRepos parseLinkRepos) {
        this.parseLinkRepos = parseLinkRepos;
    }

    @Override
    public List<ParseLinkEntity> findAll() {
        return parseLinkRepos.findAll();
    }

    @Override
    public void saveLink(ParseLinkEntity parseLinkEntity) {
        parseLinkRepos.save(parseLinkEntity);
    }

    @Override
    public void deleteLinkById(Long id) {
        parseLinkRepos.deleteById(id);
    }
}
