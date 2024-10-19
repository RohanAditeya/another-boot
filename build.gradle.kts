import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    `java-library`
    alias(libs.plugins.spring.boot.gradle.plugin) apply false
}

allprojects {

    group = "com.framework.another.boot"
    version = "2.0.0-SNAPSHOT"

    apply(plugin = "java-library")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
    }

    ext {
        set("springCloudVersion", "2023.0.2")
        set("logbackEcsEncoderVersion", "1.6.0")
        set("reflectionsVersion", "0.10.2")
    }

    the<DependencyManagementExtension>().apply {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${project.ext.get("springCloudVersion")}")
        }
        dependencies {
            dependency("co.elastic.logging:logback-ecs-encoder:${project.ext.get("logbackEcsEncoderVersion")}")
            dependency("org.reflections:reflections:${project.ext.get("reflectionsVersion")}")
        }
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }
}