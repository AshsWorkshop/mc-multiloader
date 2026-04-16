import io.github.wasabithumb.jtoml.JToml
import io.github.wasabithumb.jtoml.KToml
import io.github.wasabithumb.jtoml.set
import io.github.wasabithumb.jtoml.value.array.TomlArray
import io.github.wasabithumb.jtoml.value.table.TomlTable
import org.gradle.api.Task
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.provideDelegate
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.util.Locale
import net.ashwork.gradle.multiloader.*

plugins {
    id("multiloader-base")
    id("net.neoforged.moddev")
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

enum class VersionPart(val componentIndex: Int) {
    MAJOR(0),
    MINOR(1),
    PATCH(2)
}

fun computeNextVersion(version: String, to: VersionPart = VersionPart.MINOR): String {
    val versionComponents = version.split(".").subList(0, to.componentIndex + 1).toMutableList()
    val toUpdate: Int = versionComponents.removeLast().toInt()
    return "${versionComponents.joinToString(".")}.${toUpdate + 1}"
}

internal val modFile: TaskProvider<Task> = tasks.register("generateModFile") {
    // Base toml
    val modsToml = TomlTable.create()
    modsToml["license"] = resolveProperty("mod_license")

    // Mod entries
    val mods = TomlArray.create()
    modsToml["mods"] = mods

    // Mod
    val mod = TomlTable.create()
    mod["modId"] = resolveProperty("mod_id")
    mod["version"] = resolveProperty("mod_version")
    mod["displayName"] = resolveProperty("mod_name")
    mod["authors"] = resolveProperty("mod_authors")
    mod["description"] = resolveProperty("mod_description")
    mods.add(mod)

    // Mod dependencies
    val modDependencies = TomlArray.create()
    modsToml["dependencies.${resolveProperty("mod_id")}"] = modDependencies

    // Minecraft dependency
    val minecraft = TomlTable.create()
    minecraft["modId"] = "minecraft"
    minecraft["type"] = "required"
    minecraft["versionRange"] = "[${resolveProperty("vanillaMinecraft")},${computeNextVersion(resolveProperty("vanillaMinecraft"))})"
    minecraft["ordering"] = "AFTER"
    minecraft["side"] = "BOTH"
    modDependencies.add(minecraft)

    // NeoForge dependency
    val neoForge = TomlTable.create()
    neoForge["modId"] = "neoforge"
    neoForge["type"] = "required"
    neoForge["versionRange"] = "[${resolveProperty("neoforgeApi")},${computeNextVersion(resolveProperty("neoforgeApi"))})"
    neoForge["ordering"] = "AFTER"
    neoForge["side"] = "BOTH"
    modDependencies.add(neoForge)

    // Write to file
    val outputDir: File = layout.buildDirectory.asFile.get().resolve("generated/sources/mod_file")
    val filePath: File = outputDir.resolve("META-INF/neoforge.mods.toml")
    Files.createDirectories(filePath.parentFile.toPath())
    FileWriter(filePath, StandardCharsets.UTF_8).use { KToml.write(it, modsToml) }
    outputs.dir(outputDir)
}

client.resources {
    srcDir(modFile)
    source(generated.resources)
    exclude("./cache")
}

neoForge {
    version = resolveProperty("neoforgeApi")

    // Sync tasks
    ideSyncTask(modFile)

    mods.create(resolveProperty("mod_id")) {
        sourceSet(client)
        sourceSet(data)
    }

    runs {
        create("client") {
            client()
        }
        create("server") {
            server()
            programArgument("--nogui")
        }
        create("gameTestServer") {
            type = "gameTestServer"
        }
        create("clientData") {
            clientData()
            programArguments.addAll("--mod", resolveProperty("mod_id"), "--all", "--output", generated.resources.srcDirs.first().absolutePath)
            client.resources.srcDirs.forEach { programArguments.addAll("--existing", it.absolutePath) }
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
        named { !it.lowercase(Locale.ROOT).contains("data") }.configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", resolveProperty("mod_id"))
        }
    }
}