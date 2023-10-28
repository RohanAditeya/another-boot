package com.framework.another.boot;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class AnotherBootCoreBannerTest {

    private final AnotherBootCoreBanner anotherBootCoreBanner = new AnotherBootCoreBanner();
    @Test
    public void printBannerTest () {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        anotherBootCoreBanner.printBanner(null, null, new PrintStream(outputStream));
        String actual = outputStream.toString();
        assertThat(actual).startsWith(String.join("\r\n", AnotherBootCoreBanner.ANOTHER_BOOT_BANNER));
    }
}