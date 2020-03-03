package com.dmitriy.shortener.repository;

import com.dmitriy.shortener.model.UrlStorageEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaUrlStorageRepository extends JpaRepositoryImplementation<UrlStorageEntity, Long> {

    UrlStorageEntity findByShortUrl(String shortUrl);

    UrlStorageEntity findByOriginalUrl(String originalUrl);
}
