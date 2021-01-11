package com.dmitriy.shortener.controller;

import com.dmitriy.shortener.TestBase;
import com.dmitriy.shortener.domain.UrlStorageEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UrlShortenerControllerTest extends TestBase {

    private static final String BASE_PATH = "/rest/url/";

    private static final String ORIGINAL_URL = "https://www.google.com/";
    private static final String SHORT_URL = "cac87a2c";
    private static final String INVALID_URL = "abc";

    @Test
    public void testCreateShortUrl() throws Exception {
        mockMvc.perform(post(BASE_PATH)
                .content(json(ORIGINAL_URL))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verifyCollectionState(UrlStorageEntity.class, List.of(
                allOf(
                        hasProperty("originalUrl", equalTo(ORIGINAL_URL))
                )
        ));
    }

    @Test
    public void testRetriveOriginalUrl() throws Exception {
        given().urls().with(url -> {
            url.setOriginalUrl(ORIGINAL_URL);
            url.setShortUrl(SHORT_URL);
        }).buildSingle();

        mockMvc.perform(get(BASE_PATH + SHORT_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(ORIGINAL_URL));
    }

    @Test
    public void testUrlNotFound() throws Exception {
        mockMvc.perform(get(BASE_PATH + INVALID_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testInvalidUrl() throws Exception {
        mockMvc.perform(post(BASE_PATH)
                .content(json(INVALID_URL))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
