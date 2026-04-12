import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.provideDelegate

plugins {
    id("multiloader-base")
    id("net.fabricmc.fabric-loom")
}

val rootProperty: (String) -> String by rootProject.extra

dependencies {
    minecraft("com.mojang:minecraft:${rootProperty("vanillaMinecraft")}")
    implementation("net.fabricmc:fabric-loader:${rootProperty("fabricLoader")}")
    implementation("net.fabricmc.fabric-api:fabric-api:${rootProperty("fabricApi")}")
}
