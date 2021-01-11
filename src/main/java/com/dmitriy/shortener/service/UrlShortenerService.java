package com.dmitriy.shortener.service;

import com.dmitriy.shortener.converter.DomainConverter;
import com.dmitriy.shortener.domain.UrlStorageEntity;
import com.dmitriy.shortener.dto.UrlDTO;
import com.dmitriy.shortener.exception.InvalidUrlException;
import com.dmitriy.shortener.exception.UrlNotFoundException;
import com.dmitriy.shortener.repository.UrlStorageRepository;
import com.google.common.hash.Hashing;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    private final UrlStorageRepository urlStorageRepository;
    private final DomainConverter domainConverter;

    public UrlDTO create(String requestURL, String originalUrl) {

        var urlValidator = new UrlValidator(
                new String[]{"http", "https"}
        );

        if (urlValidator.isValid(originalUrl)) {
            var shortUrl = Hashing.murmur3_32().hashString(originalUrl, StandardCharsets.UTF_8).toString();
            var entity = domainConverter.toUrlStorageEntity(originalUrl, shortUrl);
            var currentEntity = urlStorageRepository.findByOriginalUrl(entity.getOriginalUrl())
                    .orElse(urlStorageRepository.save(entity));
            return domainConverter.toUrlDTO(requestURL + "/" + currentEntity.getShortUrl());
        }

        throw new InvalidUrlException("Url invalid: " + originalUrl);
    }

    public UrlStorageEntity findByShortUrl(String shortUrl) {
        return urlStorageRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Original url for short url " + shortUrl + " was not found"));
    }
}
