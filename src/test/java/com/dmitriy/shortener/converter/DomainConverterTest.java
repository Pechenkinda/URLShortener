package com.dmitriy.shortener.converter;

import com.dmitriy.shortener.TestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DomainConverterTest extends TestBase {

    @Test
    void testToUrlStorageEntity() {
        var originalUrl = random.nextObject(String.class);
        var shortUrl = random.nextObject(String.class);

        var actual = domainConverter.toUrlStorageEntity(originalUrl, shortUrl);

        assertEquals(originalUrl, actual.getOriginalUrl());
        assertEquals(shortUrl, actual.getShortUrl());
    }

    @Test
    void testToUrlDTO() {
        var url = random.nextObject(String.class);

        var actual = domainConverter.toUrlDTO(url);

        assertEquals(url, actual.getUrl());
    }
}
