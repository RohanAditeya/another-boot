package com.framework.another.boot;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.ansi.AnsiStyle;
import org.springframework.boot.system.JavaVersion;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * Default banner implementation which writes the 'Another' banner
 * @implNote  Java version printed is detected by the convenience method
 * {@link JavaVersion#getJavaVersion()} provided by Spring. If the java version
 * cannot be detected then the default return value is used.
 * @author Rohan Aditeya
 */
class AnotherBootCoreBanner implements Banner {

    protected static final String[] ANOTHER_BOOT_BANNER = {
            "",
            "  .      _                _   _                ______",
            " /\\\\    / \\   _ __   ___ | |_| |__   ___ _ __  \\ \\ \\ \\",
            "( ( )  / _ \\ | '_ \\ / _ \\| __| '_ \\ / _ \\ '__|  \\ \\ \\ \\",
            " \\\\/  / ___ \\| | | | (_) | |_| | | |  __/ |     / / / /",
            "  '  /_/   \\_\\_| |_|\\___/ \\__|_| |_|\\___|_|    /_/_/_/",
            " =============================================/_/_/_/"
    };
    private static final String SPRING_BOOT_TEXT = ":: Spring-Boot ::";
    private static final String ANOTHER_BOOT_TEXT = ":: Another-Boot ::";
    private static final String ANOTHER_BOOT_VERSION = AnotherBootCoreBanner.class.getPackage().getImplementationVersion();
    private static final int BANNER_MAX_LENGTH = 55;

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        // print banner text
        for (String linesInBanner: ANOTHER_BOOT_BANNER)
            out.println(linesInBanner);

        // print another boot version
        printVersionInfo(ANOTHER_BOOT_TEXT, ANOTHER_BOOT_VERSION, out);

        // print spring boot version
        printVersionInfo(SPRING_BOOT_TEXT, SpringBootVersion.getVersion(), out);
        out.println();
    }

    private void printVersionInfo (String text, String version, PrintStream out) {
        StringBuilder padding = new StringBuilder();
        version = "(v" + version + ")";
        while (padding.length() < BANNER_MAX_LENGTH - (text.length() + version.length()))
            padding.append(" ");
        out.println(AnsiOutput.toString(AnsiColor.GREEN, text, AnsiColor.DEFAULT, padding.toString(),
                AnsiStyle.FAINT, version));
    }
}