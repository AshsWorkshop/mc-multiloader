import groovy.json.JsonOutput
import net.fabricmc.loom.configuration.ide.idea.IdeaSyncTask
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.provideDelegate
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files

plugins {
    id("multiloader-base")
    id("net.fabricmc.fabric-loom")
}

val rootProperty: (String) -> String by rootProject.extra
val modId = "${rootProperty("mod_id")}_${project.name.replace("-", "_")}"

dependencies {
    minecraft("com.mojang:minecraft:${rootProperty("vanillaMinecraft")}")
    implementation("net.fabricmc:fabric-loader:${rootProperty("fabricLoader")}")
    implementation("net.fabricmc.fabric-api:fabric-api:${rootProperty("fabricApi")}")
}

internal val modFile: TaskProvider<Task> = tasks.register("generateModFile") {
    // Base json
    val modsJson: Map<String, Any> = mapOf(
        "schemaVersion" to 1,
        "id" to modId,
        "version" to rootProperty("mod_version"),
        "name" to "${rootProperty("mod_name")} (${project.name})",
        "description" to rootProperty("mod_description"),
        "authors" to rootProperty("mod_authors").split(",").map { it.trim() },
        "license" to rootProperty("mod_license"),
        "environment" to "*",
        "entrypoints" to mapOf<String, List<String>>(),
        "mixins" to listOf<String>(),
        "depends" to mapOf(
            "java" to rootProperty("javaVersion"),
            "minecraft" to "~${rootProperty("vanillaMinecraft")}",
            "fabricloader" to ">=${rootProperty("fabricLoader")}",
            "fabric-api" to ">=${rootProperty("fabricApi")}"
        )
    )

    val outputDir: File = layout.buildDirectory.asFile.get().resolve("generated/sources/mod_file")
    val filePath: File = outputDir.resolve("fabric.mod.json")
    Files.createDirectories(filePath.parentFile.toPath())
    FileWriter(filePath, StandardCharsets.UTF_8).use { it.write(JsonOutput.prettyPrint(JsonOutput.toJson(modsJson))) }
    outputs.dir(outputDir)
}

sourceSets["main"].resources {
    srcDir(modFile)
}

tasks.withType<IdeaSyncTask>().forEach {
    it.finalizedBy(modFile)
}
