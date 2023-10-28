package com.framework.another.boot;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * Application listener to set fallback banner to instance of {@link AnotherBootCoreBanner}
 * @implNote Sets the banner to the spring application directly as a fallback banner.
 * @author Rohan Aditeya
 */
class BannerInitializationListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        event.getSpringApplication().setBanner(new AnotherBootCoreBanner());
    }
}