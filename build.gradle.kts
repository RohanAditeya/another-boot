import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.plugin.SpringBootPlugin

val starterProjectName: List<String> = listOf("another-boot-bom", "another-boot-gradle-plugin")

plugins {
    `java-library`
    `maven-publish`
    alias(libs.plugins.spring.boot.gradle.plugin) apply false
}

ext {
    set("scmConnectionUrl", "https://github.com/RohanAditeya/another-framework.git")
}

allprojects {
    group = "com.framework.another.boot"
    version = "2.0.0-SNAPSHOT"

    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    ext {
        set("artifactory-password", "password")
        set("springCloudVersion", "2023.0.2")
    }

    if (!starterProjectName.contains(name)) {

        apply(plugin = "java-library")
        apply(plugin = "io.spring.dependency-management")

        ext {
            set("logbackEcsEncoderVersion", "1.6.0")
            set("reflectionsVersion", "0.10.2")
        }

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

        java {
            toolchain {
                languageVersion = JavaLanguageVersion.of(21)
            }
            withJavadocJar()
            withSourcesJar()
        }

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

    publishing {
        repositories {
            maven {
                name = "github-packages"
                url = uri("https://maven.pkg.github.com/RohanAditeya/another-framework")
                authentication {
                    register(name = "basic-authentication", type = BasicAuthentication::class)
                }
                credentials {
                    username = "RohanAditeya"
                    password = project.ext.get("artifactory-password")!!.toString()
                }
            }
        }
    }
}

publishing {
    publications {
        getByName<MavenPublication>("maven-publish") {
            pom {
                name = "another-framework"
                description = "Common framework built on top of spring boot"
                inceptionYear = "2023"
                organization {
                    name = "Another-Boot"
                    url = "TBD"
                }
                developers {
                    developer {
                        name = "Rohan Aditeya"
                        email = "rohan.aditeya@gmail.com"
                        timezone = "Asia/Calcutta"
                    }
                }
                distributionManagement {
                    downloadUrl = "https://maven.pkg.github.com/RohanAditeya/another-framework"
                }
                scm {
                    connection = "scm:git:${project.ext.get("scmConnectionUrl")}"
                    developerConnection = "scm:git:${project.ext.get("scmConnectionUrl")}"
                    tag = "HEAD"
                    url = "${project.ext.get("scmConnectionUrl")}"
                }
            }
        }
    }
}