package com.framework.another.boot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.Banner;
import org.springframework.boot.context.event.ApplicationStartingEvent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
public class BannerInitializationListenerTest {

    private final BannerInitializationListener listener = new BannerInitializationListener();

    @Test
    public void onApplicationEventTest () {
        ArgumentCaptor<Banner> bannerPrinterCaptor = ArgumentCaptor.forClass(Banner.class);
        ApplicationStartingEvent mockEvent = mock(ApplicationStartingEvent.class, RETURNS_DEEP_STUBS);
        listener.onApplicationEvent(mockEvent);
        verify(mockEvent.getSpringApplication(), times(1)).setBanner(bannerPrinterCaptor.capture());
        assertThat(bannerPrinterCaptor.getValue()).isExactlyInstanceOf(AnotherBootCoreBanner.class);
    }
}