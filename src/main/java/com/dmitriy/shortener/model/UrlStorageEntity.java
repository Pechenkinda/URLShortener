package com.dmitriy.shortener.model;

import com.dmitriy.shortener.common.Identifiable;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "url_storage", uniqueConstraints=@UniqueConstraint(columnNames = {"short_url"}))
public class UrlStorageEntity implements Identifiable<Long> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "original_url", unique = true)
    private String originalUrl;

    @Column(name = "short_url")
    private String shortUrl;
}
