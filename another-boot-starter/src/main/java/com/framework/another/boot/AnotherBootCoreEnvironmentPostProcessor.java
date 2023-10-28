package com.framework.another.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * Postprocessor to add 'another boot core' properties.
 * @author Rohan Aditeya
*/
class AnotherBootCoreEnvironmentPostProcessor implements EnvironmentPostProcessor {

    protected static final String CONFIG_SOURCE = "systemProperties";
    private static final String CORE_PROPERTY_RESOURCE = "another-boot-core.yml";
    protected static final String CORE_PROPERTY_SOURCE_NAME = "anotherBootProperties";
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            YamlPropertySourceLoader propertySourceLoader = new YamlPropertySourceLoader();
            List<PropertySource<?>> coreProperties = propertySourceLoader.load(CORE_PROPERTY_SOURCE_NAME, new ClassPathResource(CORE_PROPERTY_RESOURCE));
            environment.getPropertySources().addBefore(CONFIG_SOURCE, coreProperties.get(0));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}