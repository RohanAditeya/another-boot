plugins {
    `java-platform`
}

dependencies {
    constraints {
        api(project(":another-boot-starter"))
        api(project(":another-boot-observability-starter"))
        api(project(":another-boot-starter-webflux"))
    }
    api(platform("org.springframework.cloud:spring-cloud-dependencies:${project.ext.get("springCloudVersion")}"))
}

publishing {
    publications {
        create<MavenPublication>("maven-parent-publish") {
            from(components["javaPlatform"])
        }
    }
}

// Required for adding spring cloud bom to dependency management
javaPlatform {
    allowDependencies()
}