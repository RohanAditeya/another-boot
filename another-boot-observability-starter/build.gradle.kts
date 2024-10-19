dependencies {
    api(project(":another-boot-starter"))
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("org.springframework.boot:spring-boot-starter-web")
    api("io.micrometer:micrometer-tracing-bridge-otel")
    api("io.opentelemetry:opentelemetry-exporter-otlp")
    api("io.micrometer:micrometer-registry-otlp")
    api("co.elastic.logging:logback-ecs-encoder")
    api("org.reflections:reflections")
    api("co.elastic.logging:logback-ecs-encoder")
    api("org.reflections:reflections")
    api("io.fabric8:kubernetes-client")
}