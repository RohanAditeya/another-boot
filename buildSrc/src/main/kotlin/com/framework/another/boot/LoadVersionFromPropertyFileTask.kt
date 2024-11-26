package com.framework.another.boot

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.TaskAction
import java.util.Properties

abstract class LoadVersionFromPropertyFileTask: DefaultTask() {

    @get:InputFile
    abstract val propertyFileProvider: RegularFileProperty

    @get:Input
    abstract val versionProperty: Property<String>

    @TaskAction
    fun loadVersionFromPropertyFile() {
        val propertyFile = propertyFileProvider.get().asFile
        logger.info("Loading property from property file ${propertyFile.path}")
        val properties: Properties = Properties()
        properties.load(propertyFile.reader())
        val version: String = properties.getProperty(versionProperty.get())
        logger.info("Setting version $version to project ${project.name}")
        project.version = version
    }
}