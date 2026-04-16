import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

plugins {
    `kotlin-dsl`
}
apply(from = "${rootDir.parentFile}/properties.gradle.kts")

repositories {
    mavenLocal()
    maven {
        name = "NeoForged Maven"
        url = uri("https://maven.neoforged.net/releases")
    }
    maven {
        name = "Fabric Maven"
        url = uri("https://maven.fabricmc.net/")
    }
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    // https://projects.neoforged.net/neoforged/moddevgradle
    implementation("net.neoforged:moddev-gradle:${rootProject.extra["neoforgeGradle"]}")
    implementation("net.fabricmc:fabric-loom:${rootProject.extra["fabricLoom"]}")
    // https://repo.maven.apache.org/maven2/io/github/wasabithumb/jtoml/
    implementation("io.github.wasabithumb:jtoml:1.5.2")
    implementation("io.github.wasabithumb:jtoml-kotlin:1.5.2")
}
