package net.ashwork.gradle.multiloader

import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.extra
import kotlin.collections.plusAssign

fun Project.rootProperty(name: String): String = rootProject.extra[name].toString()

fun SourceSetContainer.createFrom(name: String, withClasspath: SourceSet, vararg withOutputs: SourceSet): SourceSet {
    return this.create(name) {
        compileClasspath += withClasspath.compileClasspath
        runtimeClasspath += withClasspath.runtimeClasspath
        withOutputs.forEach {
            compileClasspath += it.output
            runtimeClasspath += it.output
        }
    }
}
