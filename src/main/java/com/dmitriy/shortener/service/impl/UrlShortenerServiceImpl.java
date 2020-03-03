package com.dmitriy.shortener.service.impl;

import com.dmitriy.shortener.model.UrlStorageEntity;
import com.dmitriy.shortener.repository.JpaUrlStorageRepository;
import com.dmitriy.shortener.service.UrlShortenerService;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@Service
@Transactional
@RequiredArgsConstructor
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private final JpaUrlStorageRepository jpaUrlStorageRepository;

    @Override
    @Cacheable("url")
    public String createShortUrl(String originalUrl) {

        UrlStorageEntity result = jpaUrlStorageRepository.findByOriginalUrl(originalUrl);
        if (result == null) {
            String shortUrl = Hashing.murmur3_32().hashString(originalUrl, StandardCharsets.UTF_8).toString();
            UrlStorageEntity entity = new UrlStorageEntity();
            entity.setOriginalUrl(originalUrl);
            entity.setShortUrl(shortUrl);

            result = jpaUrlStorageRepository.saveAndFlush(entity);
        }

        return result.getShortUrl();
    }

    @Override
    @Cacheable("url")
    public String getOriginalUrl(String shortUrl) {
        UrlStorageEntity result = jpaUrlStorageRepository.findByShortUrl(shortUrl);

        if (result == null) {
            return null;
        }

        return result.getOriginalUrl();
    }
}
