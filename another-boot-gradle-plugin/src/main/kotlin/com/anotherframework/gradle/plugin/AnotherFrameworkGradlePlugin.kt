package com.anotherframework.gradle.plugin

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.util.VersionExtractor

class AnotherFrameworkGradlePlugin: Plugin<Project> {

    private val anotherBootVersion = VersionExtractor.forClass(AnotherFrameworkGradlePlugin::class.java)
    private val anotherBootBOMCoordinates: String = "com.framework.another.boot:another-boot-bom:${anotherBootVersion}"

    override fun apply(target: Project) {
        target.pluginManager.apply(SpringBootPlugin::class.java)
        target.pluginManager.apply(DependencyManagementPlugin::class.java)
        target.extensions.apply {
            val dependencyManagementExtension = getByType(DependencyManagementExtension::class.java)
            dependencyManagementExtension.apply {
                imports { importsHandler ->
                    importsHandler.mavenBom(anotherBootBOMCoordinates)
                }
            }
        }
    }
}