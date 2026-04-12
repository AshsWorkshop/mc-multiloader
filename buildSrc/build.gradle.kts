plugins {
    `kotlin-dsl`
}
apply(from = "${rootDir.parentFile}/properties.gradle.kts")

val rootProperty: (String) -> String by rootProject.extra

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
}

dependencies {
    // https://projects.neoforged.net/neoforged/moddevgradle
    implementation("net.neoforged:moddev-gradle:${rootProperty("neoforgeGradle")}")
    implementation("net.fabricmc:fabric-loom:${rootProperty("fabricLoom")}")
}
