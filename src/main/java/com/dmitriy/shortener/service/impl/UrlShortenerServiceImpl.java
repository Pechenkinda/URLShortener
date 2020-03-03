package com.dmitriy.shortener.service.impl;

import com.dmitriy.shortener.model.UrlStorageEntity;
import com.dmitriy.shortener.repository.JpaUrlStorageRepository;
import com.dmitriy.shortener.service.UrlShortenerService;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final JpaUrlStorageRepository jpaUrlStorageRepository;

    @Override
    @Caching(
            cacheable = {
                    @Cacheable(value = "originalUrl", key = "#entity.originalUrl")
            },
            evict = {
                    @CacheEvict(value = "shortUrl", key = "#entity.shortUrl")
            }
    )
    public String createShortUrl(UrlStorageEntity entity) {

        UrlStorageEntity result = jpaUrlStorageRepository.findByOriginalUrl(entity.getOriginalUrl());
        if (result == null) {
            result = jpaUrlStorageRepository.saveAndFlush(entity);
        }

        return result.getShortUrl();
    }

    @Override
    @Cacheable("shortUrl")
    public String getOriginalUrl(String shortUrl) {
        UrlStorageEntity result = jpaUrlStorageRepository.findByShortUrl(shortUrl);

        if (result == null) {
            return null;
        }

        return result.getOriginalUrl();
    }
}
