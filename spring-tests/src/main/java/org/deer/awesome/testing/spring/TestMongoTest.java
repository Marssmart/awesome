package org.deer.awesome.testing.spring;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = TestMongoTest.TestConfig.class)
public class TestMongoTest {

    @Container
    @RegisterExtension
    static TestMongo mongo = new TestMongo();

    @Autowired
    MongoTemplate mongoTemplate;

    @RepeatedTest(5)
    @Execution(ExecutionMode.CONCURRENT)
    void testStartedAndReady() {
        mongoTemplate.getCollectionNames().forEach(System.out::println);
    }

    @Configuration
    @EnableAutoConfiguration
    @EnableMongoRepositories
    static class TestConfig {

    }
}