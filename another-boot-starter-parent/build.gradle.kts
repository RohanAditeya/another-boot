plugins {
    `java-platform`
}

dependencies {
    constraints {
        api(project(":another-boot-starter"))
        api(project(":another-boot-observability-starter"))
        api(project(":another-boot-starter-webflux"))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven-parent-publish") {
            from(components["javaPlatform"])
        }
    }
}