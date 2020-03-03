package com.dmitriy.shortener.controller.impl;

import com.dmitriy.shortener.controller.UrlShortenerController;
import com.dmitriy.shortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest/url", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UrlShortenerControllerImpl implements UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public ResponseEntity<String> createShortUrl(String url) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"}
        );

        if (urlValidator.isValid(url)) {
            String shortUrl = urlShortenerService.createShortUrl(url);
            return ResponseEntity.ok(shortUrl);
        }

        throw new RuntimeException("Url invalid: " + url);
    }

    public ResponseEntity<String> getOriginalUrl(String shortUrl) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortUrl);

        if (originalUrl == null) {
            throw new RuntimeException("Original url for short url " + shortUrl + " was not found");
        }

        return ResponseEntity.ok(originalUrl);
    }
}
