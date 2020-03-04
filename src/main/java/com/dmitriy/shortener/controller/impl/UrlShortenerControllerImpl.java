package com.dmitriy.shortener.controller.impl;

import com.dmitriy.shortener.controller.UrlShortenerController;
import com.dmitriy.shortener.dto.UrlDTO;
import com.dmitriy.shortener.exception.UrlNotFoundException;
import com.dmitriy.shortener.model.UrlStorageEntity;
import com.dmitriy.shortener.service.UrlShortenerService;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(path = "/rest/url", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UrlShortenerControllerImpl implements UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    public ResponseEntity<UrlDTO> createShortUrl(UrlDTO urlDTO,
                                                 HttpServletRequest request) {
        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"}
        );

        String originalUrl = urlDTO.getUrl();

        if (urlValidator.isValid(originalUrl)) {
            String shortUrl = Hashing.murmur3_32().hashString(originalUrl, StandardCharsets.UTF_8).toString();
            UrlStorageEntity entity = new UrlStorageEntity();
            entity.setOriginalUrl(originalUrl);
            entity.setShortUrl(shortUrl);

            UrlStorageEntity currentEntity = urlShortenerService.create(entity);

            String fullShortUrl = request.getRequestURL() + "/" + currentEntity.getShortUrl();
            return ResponseEntity.ok(new UrlDTO(fullShortUrl));
        }

        throw new RuntimeException("Url invalid: " + originalUrl);
    }

    public ResponseEntity<Void> retriveOriginalUrl(String shortUrl) {
        UrlStorageEntity entity = urlShortenerService.findByShortUrl(shortUrl);

        if (entity == null) {
            throw new UrlNotFoundException("Original url for short url " + shortUrl + " was not found");
        }

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", entity.getOriginalUrl())
                .build();
    }
}
