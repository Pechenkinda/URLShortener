package com.dmitriy.shortener.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = MongoCollections.URLS)
public class UrlStorageEntity {

    @Id
    private String id;
    private String originalUrl;
    private String shortUrl;
}
