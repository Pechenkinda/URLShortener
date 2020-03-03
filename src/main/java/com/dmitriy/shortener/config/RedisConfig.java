package com.dmitriy.shortener.config;

import com.dmitriy.shortener.model.UrlStorageEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Autowired
    ObjectMapper mapper;

    @Autowired
    RedisConnectionFactory connectionFactory;

    @Bean
    RedisTemplate<String, UrlStorageEntity> redisTemplate() {
        final RedisTemplate<String, UrlStorageEntity> redisTemplate = new RedisTemplate<>();
        Jackson2JsonRedisSerializer<UrlStorageEntity> valueSerializer = new Jackson2JsonRedisSerializer<>(UrlStorageEntity.class);
        valueSerializer.setObjectMapper(mapper);
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(valueSerializer);
        return redisTemplate;
    }
}