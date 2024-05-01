package com.framework.another.boot.observability;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.impl.KubernetesClientImpl;
import io.fabric8.kubernetes.internal.KubernetesDeserializer;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.config.MeterFilter;
import org.reflections.Reflections;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.aot.hint.MemberCategory.*;

@AutoConfiguration
@ImportRuntimeHints(value = AnotherFrameworkObservabilityAutoConfiguration.LogbackResourceHintRegistrar.class)
public class AnotherFrameworkObservabilityAutoConfiguration {

    private static final String HOSTNAME_ENV_NAME = "HOSTNAME";

    @Bean
    @ConditionalOnCloudPlatform(value = CloudPlatform.KUBERNETES)
    public MeterFilter kubernetesLabelRegistrars () {
        try (KubernetesClient kubernetesClient = new KubernetesClientBuilder().build()) {
            Pod currentPod = kubernetesClient.pods().withName(System.getenv(HOSTNAME_ENV_NAME)).get();
            ObjectMeta podMeta = currentPod.getMetadata();
            Set<Tag> commonTagSet = new HashSet<>();
            commonTagSet.add(Tag.of("namespace", podMeta.getNamespace()));
            commonTagSet.add(Tag.of("podName", podMeta.getName()));
            return MeterFilter.commonTags(commonTagSet);
        }
    }

    static class LogbackResourceHintRegistrar implements RuntimeHintsRegistrar {

        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.resources().registerResource(new ClassPathResource("another-boot-logback.xml"));
            Class<?> clazz = NamedCluster.class;
            Reflections reflections = new Reflections(clazz.getPackageName(), clazz);
            var subtypesOfKubernetesResource = reflections.getSubTypesOf(KubernetesResource.class);
            var othersToAddForReflection = List.of(KubernetesDeserializer.class, KubernetesClientImpl.class);
            var combined = new HashSet<Class<?>>();
            combined.addAll(subtypesOfKubernetesResource);
            combined.addAll(othersToAddForReflection);
            combined.addAll(resolveSerializationClasses(JsonSerialize.class, reflections));
            combined.addAll(resolveSerializationClasses(JsonDeserialize.class, reflections));
            combined.stream().filter(Objects::nonNull)
                    .forEach(c -> hints.reflection().registerType(c, DECLARED_FIELDS, INVOKE_DECLARED_METHODS, INVOKE_DECLARED_CONSTRUCTORS));
        }

        private <R extends Annotation> Set<Class<?>> resolveSerializationClasses(Class<R> annotationClazz, Reflections reflections) {
            try {
                Method method = annotationClazz.getMethod("using");
                Set<Class<?>> classes = reflections.getTypesAnnotatedWith(annotationClazz);
                return classes.stream().map(clazzWithAnnotation -> {
                            Annotation annotation = clazzWithAnnotation.getAnnotation(annotationClazz);
                            try {
                                if (annotation != null)
                                    return (Class<?>) method.invoke(annotation);
                            } catch (Exception e) {
                                ReflectionUtils.rethrowRuntimeException(e);
                            }
                            return null;
                        }).collect(Collectors.toSet());
            } catch (NoSuchMethodException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}