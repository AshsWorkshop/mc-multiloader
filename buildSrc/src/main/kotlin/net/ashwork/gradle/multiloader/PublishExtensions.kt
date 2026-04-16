package net.ashwork.gradle.multiloader

import groovy.lang.Closure
import groovy.namespace.QName
import groovy.util.Node
import groovy.util.NodeList
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectProvider
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ResolvedArtifact
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.AbstractCopyTask
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
    fun addLicense(copy: AbstractCopyTask) {
        var license = listOf(project, rootProject).asSequence().filter { it.file("LICENSE").exists() }.map { it.file("LICENSE") }.firstOrNull()
        if (license != null) {
            copy.from(license) {
                rename { "META-INF/${it}"}
            }
        }
    }

    val artifacts: MutableList<TaskProvider<Jar>> = mutableListOf(
        tasks.register<Jar>("${name}Jar") {
            group = project.group as String
            archiveBaseName.set(baseName)
            from(*(sources.map { it.output }.toTypedArray()))
            addLicense(this)
        }
    )

    if (withSources) {
        artifacts.add(
            tasks.register<Jar>("${name}JarSources") {
                group = project.group as String
                archiveBaseName.set(baseName)
                archiveClassifier.set("sources")
                from(*(sources.map { it.allSource }.toTypedArray()))
                addLicense(this)
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

fun MavenPom.compileDependency(configuration: NamedDomainObjectProvider<Configuration>, matches: (String) -> Boolean) {
    dependency(configuration, "compile", matches)
}

fun MavenPom.compileDependency(artifact: TaskProvider<Jar>) {
    dependency(artifact, "compile")
}

fun MavenPom.runtimeDependency(configuration: NamedDomainObjectProvider<Configuration>, matches: (String) -> Boolean) {
    dependency(configuration, "runtime", matches)
}

fun MavenPom.runtimeDependency(artifact: TaskProvider<Jar>) {
    dependency(artifact, "runtime")
}

fun MavenPom.dependency(configuration: NamedDomainObjectProvider<Configuration>, scope: String, matches: (String) -> Boolean) {
    dependency(configuration.get(), scope, matches)
}

fun MavenPom.dependency(configuration: Configuration, scope: String, matches: (String) -> Boolean) {
    configuration.resolvedConfiguration.resolvedArtifacts.filter { matches(it.name) }.forEach {
        dependency(it, scope)
    }
}

fun MavenPom.dependency(artifact: ResolvedArtifact, scope: String) {
    dependency(artifact.moduleVersion.id.group, artifact.name, artifact.moduleVersion.id.version, scope)
}

fun MavenPom.dependency(artifact: TaskProvider<Jar>, scope: String) {
    val jar = artifact.get()
    dependency(jar.group!!, jar.archiveBaseName.get(), jar.archiveVersion.get(), scope)
}

fun MavenPom.dependency(groupId: String, artifactId: String, version: String, scope: String) {
    withXml {
        val dependencies: Node = if (asNode().getAt(QName("dependencies")).isNotEmpty()) asNode().getAt(QName("dependencies"))[0] as Node else asNode().appendNode("dependencies")
        val dependency = dependencies.appendNode("dependency")
        dependency.appendNode("groupId", groupId)
        dependency.appendNode("artifactId", artifactId)
        dependency.appendNode("version", version)
        dependency.appendNode("scope", scope)
    }
}
