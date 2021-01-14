package org.deer.awesome.testing.spring;

import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.TestPropertySourceUtils;

public interface BaseTestContainer extends BeforeTestExecutionCallback {

    /**
     * Retrieves the ${link ConfigurableApplicationContext} if available
     */
    default ConfigurableApplicationContext getConfigurableContext(ExtensionContext extensionContext) {
        return (ConfigurableApplicationContext) SpringExtension.getApplicationContext(extensionContext);
    }

    /**
     * Adds properties to current context
     * */
    default void addPropertiesToEnvironment(ExtensionContext extensionContext, String... keyValues) {
        final ConfigurableApplicationContext configurableContext = getConfigurableContext(extensionContext);
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(configurableContext, keyValues);
    }
}
