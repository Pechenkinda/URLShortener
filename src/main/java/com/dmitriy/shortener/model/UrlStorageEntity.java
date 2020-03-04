package com.dmitriy.shortener.model;

import com.dmitriy.shortener.common.Identifiable;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "url_storage")
public class UrlStorageEntity implements Identifiable<Long>, Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "original_url", unique = true)
    private String originalUrl;

    @Column(name = "short_url", unique = true)
    private String shortUrl;
}
