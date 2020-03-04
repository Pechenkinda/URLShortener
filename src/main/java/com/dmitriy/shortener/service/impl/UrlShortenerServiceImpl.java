package com.dmitriy.shortener.service.impl;

import com.dmitriy.shortener.model.UrlStorageEntity;
import com.dmitriy.shortener.repository.JpaUrlStorageRepository;
import com.dmitriy.shortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final JpaUrlStorageRepository jpaUrlStorageRepository;

    @Override
    @CachePut(value = "url", key = "#entity.shortUrl")
    public UrlStorageEntity create(UrlStorageEntity entity) {

        UrlStorageEntity result = jpaUrlStorageRepository.findByOriginalUrl(entity.getOriginalUrl());
        if (result == null) {
            result = jpaUrlStorageRepository.saveAndFlush(entity);
        }

        return result;
    }

    @Override
    @Cacheable("url")
    public UrlStorageEntity findByShortUrl(String shortUrl) {

        return jpaUrlStorageRepository.findByShortUrl(shortUrl);
    }
}
