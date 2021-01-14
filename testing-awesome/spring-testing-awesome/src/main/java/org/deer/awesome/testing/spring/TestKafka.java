package org.deer.awesome.testing.spring;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import static java.lang.String.format;

public class TestKafka extends KafkaContainer implements BaseTestContainer {

    public static final String DEFAULT_IMAGE = "confluentinc/cp-kafka:5.5.3";
    public static final String BOOTSTRAP_SERVERS = "spring.kafka.bootstrap-servers";

    private boolean springPropsInitialized;

    public TestKafka() {
        super(DockerImageName.parse(DEFAULT_IMAGE));
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) {
        if (!springPropsInitialized) {
            addPropertiesToEnvironment(extensionContext, format("%s:%s", BOOTSTRAP_SERVERS, this.getBootstrapServers()));
            springPropsInitialized = true;
        }
    }
}
