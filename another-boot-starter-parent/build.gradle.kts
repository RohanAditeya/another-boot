import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

the<DependencyManagementExtension>().apply {
    dependencies {
        dependency("${project.group}:another-boot-starter:${project.version}")
    }
}