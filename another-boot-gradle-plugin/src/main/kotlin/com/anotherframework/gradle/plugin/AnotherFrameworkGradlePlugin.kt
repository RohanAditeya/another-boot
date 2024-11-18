package com.anotherframework.gradle.plugin

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

class AnotherFrameworkGradlePlugin: Plugin<Project> {

    private val anotherBootBOMCoordinates: String = "another-boot-starter-parent"

    override fun apply(target: Project) {
        target.plugins.apply(SpringBootPlugin::class.java)
        target.plugins.apply(DependencyManagementPlugin::class.java)
        target.extensions.apply {
            val dependencyManagementExtension = getByType(DependencyManagementExtension::class.java)
            dependencyManagementExtension.apply {
                imports { importsHandler ->
                    importsHandler.mavenBom("${target.group}:${anotherBootBOMCoordinates}:${target.version}")
                }
            }
        }
    }
}