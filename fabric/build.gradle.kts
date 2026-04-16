import groovy.json.JsonOutput
import net.fabricmc.loom.configuration.ide.idea.IdeaSyncTask
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import net.ashwork.gradle.multiloader.*

plugins {
    id("multiloader-base")
    id("net.fabricmc.fabric-loom")
}

internal val api: Project = rootProject.project(":api")

// Create source sets
internal val base: SourceSet = sourceSets.createFrom("base", sourceSets["main"], api.sourceSets["base"])
internal val common: SourceSet = sourceSets.createFrom("common", base, base, api.sourceSets["common"])
internal val client: SourceSet = sourceSets.createFrom("client", common, common, api.sourceSets["client"])
internal val data: SourceSet = sourceSets.createFrom("data", client, client, api.sourceSets["data"])

tasks.named("compileJava") {
    dependsOn(tasks.named("compileDataJava"))
}

internal val generated: SourceSet = sourceSets.create("generated") {
    java.setSrcDirs(emptyList<Any>())
}

dependencies {
    minecraft("com.mojang:minecraft:${resolveProperty("vanillaMinecraft")}")
    implementation("net.fabricmc:fabric-loader:${resolveProperty("fabricLoader")}")
    implementation("net.fabricmc.fabric-api:fabric-api:${resolveProperty("fabricApi")}")
}

internal val modFile: TaskProvider<Task> = tasks.register("generateModFile") {
    // Base json
    val modsJson: Map<String, Any> = mapOf(
        "schemaVersion" to 1,
        "id" to resolveProperty("mod_id"),
        "version" to resolveProperty("mod_version"),
        "name" to "${resolveProperty("mod_name")} (${project.name})",
        "description" to resolveProperty("mod_description"),
        "authors" to resolveProperty("mod_authors").split(",").map { it.trim() },
        "license" to resolveProperty("mod_license"),
        "environment" to "*",
        "entrypoints" to mapOf<String, List<String>>(),
        "mixins" to listOf<String>(),
        "depends" to mapOf(
            "java" to resolveProperty("javaVersion"),
            "minecraft" to "~${resolveProperty("vanillaMinecraft")}",
            "fabricloader" to ">=${resolveProperty("fabricLoader")}",
            "fabric-api" to ">=${resolveProperty("fabricApi")}"
        )
    )

    val outputDir: File = layout.buildDirectory.asFile.get().resolve("generated/sources/mod_file")
    val filePath: File = outputDir.resolve("fabric.mod.json")
    Files.createDirectories(filePath.parentFile.toPath())
    FileWriter(filePath, StandardCharsets.UTF_8).use { it.write(JsonOutput.prettyPrint(JsonOutput.toJson(modsJson))) }
    outputs.dir(outputDir)
}

client.resources {
    srcDir(modFile)
    source(generated.resources)
    exclude("./cache")
}

tasks.withType<IdeaSyncTask>().forEach {
    it.finalizedBy(modFile)
}

loom {
    runs {
        named("client") {
            client()
            configName = "Fabric Client"
        }
        named("server") {
            server()
            configName = "Fabric Server"
        }

        configureEach {
            ideConfigGenerated(true)
            source(data)
        }
    }
}

fabricApi.configureDataGeneration {
    client = true
    createRunConfiguration = true
    outputDirectory = generated.resources.srcDirs.first()
}
