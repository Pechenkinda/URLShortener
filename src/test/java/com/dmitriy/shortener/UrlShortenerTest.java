package com.dmitriy.shortener;

import com.dmitriy.shortener.service.UrlShortenerService;
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

    private static final String ORIGINAL_URL = "https://www.google.com/";
    private static final String SHORT_URL = "cac87a2c";

    @Test
    public void testCreateShortUrl() throws Exception {
        mvc.perform(post("/rest/url")
                .content(ORIGINAL_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String originalUrl = urlShortenerService.getOriginalUrl(SHORT_URL);
        assertEquals(ORIGINAL_URL, originalUrl);
    }

//    @Test
//    public void testGetOriginalUrl() throws Exception {
//        urlShortenerService.createShortUrl(ORIGINAL_URL);
//
//        mvc.perform(get("/rest/url/" + SHORT_URL)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", is(ORIGINAL_URL)));
//    }
}
