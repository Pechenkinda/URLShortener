package com.dmitriy.shortener.dsl;

import com.dmitriy.shortener.domain.UrlStorageEntity;
import com.dmitriy.shortener.repository.UrlStorageRepository;
import com.dmitriy.shortener.utils.EasyRandomTestUtils;
import lombok.RequiredArgsConstructor;
import org.jeasy.random.EasyRandom;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Given {

    private final UrlStorageRepository urlStorageRepository;
    private final EasyRandom easyRandom = EasyRandomTestUtils.create();

    public MongoEntityBuilder<UrlStorageEntity> urls() {
        return new MongoEntityBuilder<>(UrlStorageEntity.class, urlStorageRepository, easyRandom);
    }
}
