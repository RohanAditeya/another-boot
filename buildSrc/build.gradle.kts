plugins {
    kotlin("jvm") version "2.0.21"
    `kotlin-dsl`
}

repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/RohanAditeya/another-framework")
        credentials {
            username = project.findProperty("artifactory.user") as String? ?: "user"
            password = project.findProperty("artifactory.password") as String? ?: "password"
        }
        authentication {
            create<BasicAuthentication>("basic")
        }
    }
}