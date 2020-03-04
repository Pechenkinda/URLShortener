package com.dmitriy.shortener.service;

import com.dmitriy.shortener.model.UrlStorageEntity;

public interface UrlShortenerService {

    UrlStorageEntity create(UrlStorageEntity entity);

    UrlStorageEntity findByShortUrl(String shortUrl);
}
