package net.ashwork.gradle.multiloader

import groovy.lang.Closure
import groovy.namespace.QName
import groovy.util.Node
import groovy.util.NodeList
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import kotlin.text.set

fun Project.publishSourceSets(name: String, sources: List<SourceSet>, baseName: String, withSources: Boolean = true): TaskProvider<Jar> {
    return publishSourceSets(name, sources, baseName, withSources) {}
}

fun Project.publishSourceSets(name: String, sources: List<SourceSet>, baseName: String, withSources: Boolean = true, configurePom: Action<MavenPom>): TaskProvider<Jar> {
    val artifacts: MutableList<TaskProvider<Jar>> = mutableListOf(
        tasks.register<Jar>("${name}Jar") {
            group = project.group as String
            archiveBaseName.set(baseName)
            from(*(sources.map { it.output }.toTypedArray()))
        }
    )

    if (withSources) {
        artifacts.add(
            tasks.register<Jar>("${name}JarSources") {
                group = project.group as String
                archiveBaseName.set(baseName)
                archiveClassifier.set("sources")
                from(*(sources.map { it.allSource }.toTypedArray()))
            }
        )
    }

    extensions.configure<PublishingExtension>("publishing") {
        publications.create<MavenPublication>(name) {
            artifactId = baseName
            setArtifacts(artifacts)

            pom(configurePom)
        }
    }

    return artifacts[0]
}

fun MavenPom.dependency(artifact: TaskProvider<Jar>) {
    val jar = artifact.get()
    dependency(jar.group!!, jar.archiveBaseName.get(), jar.archiveVersion.get())
}

fun MavenPom.dependency(groupId: String, artifactId: String, version: String) {
    withXml {
        val dependencies: Node = if (asNode().getAt(QName("dependencies")).isNotEmpty()) asNode().getAt(QName("dependencies"))[0] as Node else asNode().appendNode("dependencies")
        val dependency = dependencies.appendNode("dependency")
        dependency.appendNode("groupId", groupId)
        dependency.appendNode("artifactId", artifactId)
        dependency.appendNode("version", version)
        dependency.appendNode("scope", "compile")
    }
}
