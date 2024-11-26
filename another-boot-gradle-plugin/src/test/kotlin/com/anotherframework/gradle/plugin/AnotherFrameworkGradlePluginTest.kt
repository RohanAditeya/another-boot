package com.anotherframework.gradle.plugin

import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AnotherFrameworkGradlePluginTest {

    @Test
    fun applyPluginTest() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply(AnotherFrameworkGradlePlugin::class.java)

        Assertions.assertTrue(project.extensions.findByType(DependencyManagementExtension::class.java) != null)
    }
}