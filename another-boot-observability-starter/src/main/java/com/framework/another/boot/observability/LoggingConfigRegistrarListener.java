package com.framework.another.boot.observability;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

public class LoggingConfigRegistrarListener implements ApplicationListener<ApplicationStartingEvent> {

    private ClassLoader classLoader;
    private final String LOGGING_CONFIG = "logging.config";

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        this.classLoader = event.getSpringApplication().getClassLoader();
        LoggingSystem loggingSystem = LoggingSystem.get(this.classLoader);
        if (loggingSystem instanceof LogbackLoggingSystem) {
            if (findConfig(getSpringConfigLocations()) == null && findConfig(getStandardConfigLocations()) == null)
                System.setProperty(LOGGING_CONFIG, "classpath:another-boot-logback.xml");
        }
    }

    private String findConfig(String[] locations) {
        for (String location : locations) {
            ClassPathResource resource = new ClassPathResource(location, this.classLoader);
            if (resource.exists()) {
                return "classpath:" + location;
            }
        }
        return null;
    }

    private String[] getSpringConfigLocations() {
        String[] locations = getStandardConfigLocations();
        for (int i = 0; i < locations.length; i++) {
            String extension = StringUtils.getFilenameExtension(locations[i]);
            locations[i] = locations[i].substring(0, locations[i].length() - extension.length() - 1) + "-spring."
                    + extension;
        }
        return locations;
    }

    private String[] getStandardConfigLocations() {
        return new String[] { "logback-test.groovy", "logback-test.xml", "logback.groovy", "logback.xml" };
    }
}