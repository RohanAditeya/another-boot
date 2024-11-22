import java.util.jar.Attributes

plugins {
    `java-gradle-plugin`
    kotlin("jvm") version "2.0.21"
    alias(libs.plugins.gradle.publish.plugin)
}

dependencies {
    implementation(libs.springBootGradlePlugin)
    implementation(libs.springBootDependencyManagementPlugin)
    testImplementation(platform(libs.junitBom))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher") {
        because("Only needed to run tests in a version of IntelliJ IDEA that bundles older versions")
    }
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
}

gradlePlugin {
    plugins {
        create("AnotherBootGradlePlugin") {
            id = "${project.group}"
            implementationClass = "com.anotherframework.gradle.plugin.AnotherFrameworkGradlePlugin"
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    withJavadocJar()
    withSourcesJar()
}

kotlin {
    jvmToolchain(21)
}

tasks.jar {
    manifest {
        attributes(mapOf(Attributes.Name.IMPLEMENTATION_VERSION.toString() to project.version))
    }
}

tasks.test {
    useJUnitPlatform()
}