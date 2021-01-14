package org.deer.awesome.testing.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(classes = TestKafkaTest.TestConfig.class)
public class TestKafkaTest {

    @Container
    @RegisterExtension
    static TestKafka kafka = new TestKafka();

    @Autowired
    KafkaTemplate<Object, Object> kafkaTemplate;

    @Test
    void test() {
        kafkaTemplate.send("test-topic", "test-data").completable().join();
    }

    @EnableKafka
    @Configuration
    @EnableAutoConfiguration
    static class TestConfig {

    }
}
