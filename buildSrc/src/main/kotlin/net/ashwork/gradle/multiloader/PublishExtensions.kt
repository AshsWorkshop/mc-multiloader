package net.ashwork.gradle.multiloader

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPom
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.create

fun Project.publication(configurePom: Action<MavenPom>) {
    extensions.configure<PublishingExtension>("publishing") {
        publications.create<MavenPublication>("mavenJava") {
            from(components.getByName("java"))
            pom(configurePom)
        }
    }
}
