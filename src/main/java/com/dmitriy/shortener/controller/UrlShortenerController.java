package com.dmitriy.shortener.controller;

import com.dmitriy.shortener.dto.UrlDTO;
import com.dmitriy.shortener.service.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/rest/url", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @PostMapping
    public ResponseEntity<UrlDTO> createShortUrl(@RequestBody UrlDTO urlDTO,
                                                 HttpServletRequest request) {

        var url = urlShortenerService.create(request.getRequestURL().toString(), urlDTO.getUrl());
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> retriveOriginalUrl(@PathVariable String shortUrl) {
        var entity = urlShortenerService.findByShortUrl(shortUrl);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", entity.getOriginalUrl())
                .build();
    }
}
