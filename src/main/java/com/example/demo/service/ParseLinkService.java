package com.example.demo.service;

import com.example.demo.entity.ParseLinkEntity;

import java.util.List;

public interface ParseLinkService {

    List<ParseLinkEntity> findAll();
    void saveLink(ParseLinkEntity parseLinkEntity);

    void deleteLinkById(Long id);

}
