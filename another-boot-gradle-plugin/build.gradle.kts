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
            id = "com.anotherframework.boot"
            implementationClass = "com.anotherframework.gradle.plugin.AnotherFrameworkGradlePlugin"
        }
    }
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}