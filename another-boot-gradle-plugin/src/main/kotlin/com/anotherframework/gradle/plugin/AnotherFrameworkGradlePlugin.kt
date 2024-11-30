package com.anotherframework.gradle.plugin

import io.spring.gradle.dependencymanagement.DependencyManagementPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.springframework.boot.gradle.plugin.SpringBootPlugin
import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.api.plugins.JavaLibraryPlugin
import org.springframework.boot.gradle.util.VersionExtractor

class AnotherFrameworkGradlePlugin: Plugin<Project> {

    private val anotherBootVersion = VersionExtractor.forClass(AnotherFrameworkGradlePlugin::class.java)
    private val anotherBootBOMCoordinates: String = "com.framework.another.boot:another-boot-bom:${anotherBootVersion}"

    override fun apply(target: Project) {
        val isLibraryPluginApplied = target.plugins.findPlugin(JavaLibraryPlugin::class.java) != null
        if (isLibraryPluginApplied) {
            target.pluginManager.apply(DependencyManagementPlugin::class.java)
            target.extensions.apply {
                val dependencyManagementExtension = getByType(DependencyManagementExtension::class.java)
                dependencyManagementExtension.apply {
                    imports { importsHandler ->
                        importsHandler.mavenBom(anotherBootBOMCoordinates)
                        importsHandler.mavenBom(SpringBootPlugin.BOM_COORDINATES)
                    }
                }
            }
        } else {
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
}