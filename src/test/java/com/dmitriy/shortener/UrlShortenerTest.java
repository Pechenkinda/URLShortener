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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yaml")
public class UrlShortenerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String ORIGINAL_URL = "https://www.google.com/";
    private static final String SHORT_URL = "cac87a2c";

    @Test
    public void testCreateShortUrl() throws Exception {
        mvc.perform(post("/rest/url")
                .content(asJsonString(ORIGINAL_URL))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String originalUrl = urlShortenerService.getOriginalUrl(SHORT_URL);
        assertEquals(ORIGINAL_URL, originalUrl);
    }

    @Test
    public void testGetOriginalUrl() throws Exception {
        UrlStorageEntity entity = new UrlStorageEntity();
        entity.setOriginalUrl(ORIGINAL_URL);
        entity.setShortUrl(SHORT_URL);

        urlShortenerService.createShortUrl(entity);

        mvc.perform(get("/rest/url/" + SHORT_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url", is(ORIGINAL_URL)));
    }

    private String asJsonString(String url) {
        try {
            return objectMapper.writeValueAsString(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
