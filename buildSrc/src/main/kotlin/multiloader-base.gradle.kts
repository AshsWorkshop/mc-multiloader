import groovy.json.JsonSlurper
import net.ashwork.gradle.multiloader.resolveProperty

plugins {
    java
    idea
    id("multiloader-publishing")
}

base.archivesName = "${resolveProperty("mod_id")}-${project.name}"
group = resolveProperty("mod_group")
project.extra["mod_version"] = "${resolveProperty("mod_version")}.${resolveProperty("mod_version_patch")}"
version = "${resolveProperty("mod_version")}+${resolveProperty("vanillaMinecraft")}"

java {
    withSourcesJar()
    toolchain.languageVersion.set(
        JavaLanguageVersion.of(resolveProperty("java_version"))
    )
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

idea.module {
    isDownloadJavadoc = true
    isDownloadSources = true
}
