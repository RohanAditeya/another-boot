dependencies {
    api(project(":another-boot-starter"))
    api("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web") //keep optional so that web is not enforced.
    api("io.micrometer:micrometer-tracing-bridge-otel")
    api("io.opentelemetry:opentelemetry-exporter-otlp")
    api("io.micrometer:micrometer-registry-otlp")
    api("co.elastic.logging:logback-ecs-encoder")
    api("org.reflections:reflections")
    api("co.elastic.logging:logback-ecs-encoder")
    api("org.reflections:reflections")
    api("io.fabric8:kubernetes-client")
}