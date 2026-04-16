import net.ashwork.gradle.multiloader.rootProperty

plugins {
    java
    idea
}

base.archivesName = "${rootProperty("mod_id")}-${project.name}"
group = rootProperty("mod_group")
version = rootProperty("mod_version")

java.toolchain.languageVersion.set(
    JavaLanguageVersion.of(rootProperty("javaVersion"))
)

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

idea.module {
    isDownloadJavadoc = true
    isDownloadSources = true
}
