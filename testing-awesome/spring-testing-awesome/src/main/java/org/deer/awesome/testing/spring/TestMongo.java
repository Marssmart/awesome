package org.deer.awesome.testing.spring;


import com.github.dockerjava.api.command.InspectContainerResponse;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MongoDBContainer;

import java.util.Optional;

import static java.lang.String.format;

public class TestMongo extends MongoDBContainer implements BaseTestContainer {

    public static final String DEFAULT_IMAGE = "mongo:4.4.3";
    public static final String DATABASE_PROP = "spring.data.mongodb.database";
    public static final String URI_PROP = "spring.data.mongodb.uri";

    private boolean springPropsInitialized;

    public TestMongo() {
        super(DEFAULT_IMAGE);
    }

    @Override
    public void beforeTestExecution(ExtensionContext extensionContext) {
        if (!springPropsInitialized) {
            final ConfigurableApplicationContext context = getConfigurableContext(extensionContext);

            final String url = Optional.of(context.getEnvironment())
                    .map(a -> a.getProperty(DATABASE_PROP))
                    .map(this::getReplicaSetUrl)
                    .orElse(this.getReplicaSetUrl());

            addPropertiesToEnvironment(extensionContext, format("%s=%s", URI_PROP, url));
            springPropsInitialized = true;
        }
    }
}
