package com.dmitriy.shortener.service;

public interface UrlShortenerService {

    String createShortUrl(String originalUrl);

    String getOriginalUrl(String shortUrl);
}
