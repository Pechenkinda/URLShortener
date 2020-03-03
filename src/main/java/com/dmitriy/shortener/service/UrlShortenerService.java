package com.dmitriy.shortener.service;

import com.dmitriy.shortener.model.UrlStorageEntity;

public interface UrlShortenerService {

    String createShortUrl(UrlStorageEntity entity);

    String getOriginalUrl(String shortUrl);
}
