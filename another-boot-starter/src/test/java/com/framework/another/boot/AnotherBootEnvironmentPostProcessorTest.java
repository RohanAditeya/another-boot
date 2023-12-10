package com.framework.another.boot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.mock.env.MockEnvironment;

import java.util.Map;

import static com.framework.another.boot.AnotherBootCoreEnvironmentPostProcessor.CONFIG_SOURCE;
import static com.framework.another.boot.AnotherBootCoreEnvironmentPostProcessor.CORE_PROPERTY_SOURCE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@ExtendWith(value = MockitoExtension.class)
public class AnotherBootEnvironmentPostProcessorTest {

    private final AnotherBootCoreEnvironmentPostProcessor postProcessor = new AnotherBootCoreEnvironmentPostProcessor();

    @Test
    public void postProcessEnvironmentTest () {
        ConfigurableEnvironment environment = new MockEnvironment();
        environment.getPropertySources().addFirst(new SystemEnvironmentPropertySource(CONFIG_SOURCE, mock(Map.class)));
        postProcessor.postProcessEnvironment(environment, new SpringApplication());
        assertThat(environment.getPropertySources().contains(CORE_PROPERTY_SOURCE_NAME)).isTrue();
    }
}