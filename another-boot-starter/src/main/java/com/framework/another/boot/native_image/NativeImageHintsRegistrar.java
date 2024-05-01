package com.framework.another.boot.native_image;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.ClassPathResource;

@AutoConfiguration
@ImportRuntimeHints(value = NativeImageHintsRegistrar.CoreHintsRegistrar.class)
public class NativeImageHintsRegistrar {

    static class CoreHintsRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerResource(new ClassPathResource("another-boot-core.yml"));
        }
    }
}