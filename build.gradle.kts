import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import java.util.jar.Attributes

val starterProjectName: List<String> = listOf("another-boot-bom", "another-boot-gradle-plugin")

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.spring.boot.gradle.plugin) apply false
    alias(libs.plugins.gradle.release.plugin)
}

ext {
    set("scmConnectionUrl", "https://github.com/RohanAditeya/another-framework.git")
}

subprojects {
    group = "com.framework.another.boot"
    version = "2.0.0-SNAPSHOT"

    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    ext {
        set("springCloudVersion", "2023.0.2")
    }

    if (!starterProjectName.contains(name)) {

        apply(plugin = "java-library")
        apply(plugin = "io.spring.dependency-management")

        ext {
            set("logbackEcsEncoderVersion", "1.6.0")
            set("reflectionsVersion", "0.10.2")
        }

        // Adding required dependency management on all sub projects
        the<DependencyManagementExtension>().apply {
            imports {
                mavenBom(SpringBootPlugin.BOM_COORDINATES) // Applying the Spring boot BOM this way without the plugin since we do not need the plugin.
                mavenBom("org.springframework.cloud:spring-cloud-dependencies:${project.ext.get("springCloudVersion")}")
            }
            dependencies {
                dependency("co.elastic.logging:logback-ecs-encoder:${project.ext.get("logbackEcsEncoderVersion")}")
                dependency("org.reflections:reflections:${project.ext.get("reflectionsVersion")}")
            }
        }

        // Publishing config for sub libraries.
        publishing {
            publications {
                create<MavenPublication>("maven-publish") {
                    from(components["java"])
                    versionMapping {
                        usage("java-api") {
                            fromResolutionOf("runtimeClasspath")
                        }
                        usage("java-runtime") {
                            fromResolutionResult()
                        }
                    }
                }
            }
        }
    }

    // Publishing details for all projects
    publishing {
        repositories {
            maven {
                name = "github-packages"
                url = uri("https://maven.pkg.github.com/RohanAditeya/another-framework")
                authentication {
                    register(name = "basic-authentication", type = BasicAuthentication::class)
                }
                credentials {
                    username = project.findProperty("artifactory.user") as String? ?: "user"
                    password = project.findProperty("artifactory.password") as String? ?: "password"
                }
            }
        }
    }

    // Add manifest entries if java plugin is applied to a sub-project
    pluginManager.withPlugin("java") {
        java {
            toolchain {
                languageVersion = JavaLanguageVersion.of(21)
            }
            withJavadocJar()
            withSourcesJar()
        }

        tasks.jar {
            manifest {
                attributes(mapOf(Attributes.Name.IMPLEMENTATION_VERSION.toString() to project.version))
            }
        }
    }
}