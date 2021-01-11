package com.dmitriy.shortener.repository;

import com.dmitriy.shortener.domain.UrlStorageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlStorageRepository extends MongoRepository<UrlStorageEntity, String> {

    Optional<UrlStorageEntity> findByShortUrl(String shortUrl);

    Optional<UrlStorageEntity> findByOriginalUrl(String originalUrl);
}
