package com.dmitriy.shortener.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UrlShortenerController {

    @PostMapping
    ResponseEntity<String> createShortUrl(@RequestBody String originalUrl);

    @GetMapping("/{shortUrl}")
    ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl);
}
