import io.github.wasabithumb.jtoml.JToml
import io.github.wasabithumb.jtoml.KToml
import io.github.wasabithumb.jtoml.set
import io.github.wasabithumb.jtoml.value.array.TomlArray
import io.github.wasabithumb.jtoml.value.table.TomlTable
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files

plugins {
    id("multiloader-base")
    id("net.neoforged.moddev")
}

val rootProperty: (String) -> String by rootProject.extra
val modId = "${rootProperty("mod_id")}_${project.name.replace("-", "_")}"

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
    modsToml["license"] = rootProperty("mod_license")

    // Mod entries
    val mods = TomlArray.create()
    modsToml["mods"] = mods

    // Mod
    val mod = TomlTable.create()
    mod["modId"] = modId
    mod["version"] = rootProperty("mod_version")
    mod["displayName"] = "${rootProperty("mod_name")} (${project.name})"
    mod["authors"] = rootProperty("mod_authors")
    mod["description"] = rootProperty("mod_description")
    mods.add(mod)

    // Mod dependencies
    val modDependencies = TomlArray.create()
    modsToml["dependencies.${modId}"] = modDependencies

    // Minecraft dependency
    val minecraft = TomlTable.create()
    minecraft["modId"] = "minecraft"
    minecraft["type"] = "required"
    minecraft["versionRange"] = "[${rootProperty("vanillaMinecraft")},${computeNextVersion(rootProperty("vanillaMinecraft"))})"
    minecraft["ordering"] = "AFTER"
    minecraft["side"] = "BOTH"
    modDependencies.add(minecraft)

    // NeoForge dependency
    val neoForge = TomlTable.create()
    neoForge["modId"] = "neoforge"
    neoForge["type"] = "required"
    neoForge["versionRange"] = "[${rootProperty("neoforgeApi")},${computeNextVersion(rootProperty("neoforgeApi"))})"
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

sourceSets["main"].resources {
    srcDir(modFile)
}

tasks.named("processResources") {
    dependsOn(tasks.named("compileJava"))
}

neoForge {
    version = rootProperty("neoforgeApi")

    // Sync tasks
    ideSyncTask(modFile)

    mods.create(modId) {
        sourceSet(sourceSets["main"])
    }
}
