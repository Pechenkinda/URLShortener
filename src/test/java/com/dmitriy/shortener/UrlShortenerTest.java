package com.dmitriy.shortener;

import com.dmitriy.shortener.model.UrlStorageEntity;
import com.dmitriy.shortener.service.UrlShortenerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UrlShortenerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_PATH = "/rest/url/";

    private static final String ORIGINAL_URL = "https://www.google.com/";
    private static final String SHORT_URL = "cac87a2c";
    private static final String INVALID_URL = "abc";

    @Test
    public void testCreateShortUrl() throws Exception {
        mvc.perform(post(BASE_PATH)
                .content(asJsonString(ORIGINAL_URL))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        UrlStorageEntity entity = urlShortenerService.findByShortUrl(SHORT_URL);
        assertEquals(ORIGINAL_URL, entity.getOriginalUrl());
    }

    @Test
    public void testRetriveOriginalUrl() throws Exception {
        UrlStorageEntity entity = new UrlStorageEntity();
        entity.setOriginalUrl(ORIGINAL_URL);
        entity.setShortUrl(SHORT_URL);

        urlShortenerService.create(entity);

        mvc.perform(get(BASE_PATH + SHORT_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl(ORIGINAL_URL));
    }

    @Test
    public void testUrlNotFound() throws Exception {
        mvc.perform(get(BASE_PATH + INVALID_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testInvalidUrl() throws Exception {
        mvc.perform(post(BASE_PATH)
                .content(asJsonString(INVALID_URL))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String asJsonString(String url) {
        try {
            return objectMapper.writeValueAsString(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
