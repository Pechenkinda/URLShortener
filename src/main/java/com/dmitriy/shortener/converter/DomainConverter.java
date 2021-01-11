package com.dmitriy.shortener.converter;

import com.dmitriy.shortener.config.ConverterConfig;
import com.dmitriy.shortener.domain.UrlStorageEntity;
import com.dmitriy.shortener.dto.UrlDTO;
import org.mapstruct.Mapper;

@Mapper(config = ConverterConfig.class)
public interface DomainConverter {

    UrlStorageEntity toUrlStorageEntity(String originalUrl, String shortUrl);

    UrlDTO toUrlDTO(String url);
}
