package com.dmitriy.shortener.controller;

import com.dmitriy.shortener.dto.UrlDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface UrlShortenerController {

    @PostMapping
    ResponseEntity<UrlDTO> createShortUrl(@RequestBody UrlDTO originalUrl);

    @GetMapping("/{shortUrl}")
    ResponseEntity<UrlDTO> getOriginalUrl(@PathVariable String shortUrl);
}
