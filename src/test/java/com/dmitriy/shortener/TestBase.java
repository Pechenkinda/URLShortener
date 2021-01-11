package com.dmitriy.shortener;

import com.dmitriy.shortener.converter.DomainConverter;
import com.dmitriy.shortener.dsl.Given;
import com.dmitriy.shortener.utils.EasyRandomTestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.SneakyThrows;
import org.hamcrest.Matcher;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@SpringBootTest
@ActiveProfiles({"test"})
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
public class TestBase {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    protected DomainConverter domainConverter;
    @Autowired
    private Given given;

    protected EasyRandom random = EasyRandomTestUtils.create();

    @BeforeEach
    public void setUp() {
        mongoTemplate.getDb().drop();
        WireMock.reset();
    }

    public Given given() {
        return given;
    }

    @SneakyThrows
    protected String json(Object object) {
        return objectMapper.writeValueAsString(object);
    }

    protected <T> void verifyCollectionState(Class<T> entityClass, Collection<Matcher<? super T>> matchers) {
        var actualItems = mongoTemplate.findAll(entityClass);
        assertThat(
                "Collection for " + entityClass + " should contain",
                actualItems,
                containsInAnyOrder(matchers)
        );
    }
}
