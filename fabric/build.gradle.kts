import groovy.json.JsonOutput
import net.ashwork.gradle.multiloader.configureInheritingFeature
import net.ashwork.gradle.multiloader.publication
import net.ashwork.gradle.multiloader.resolveProperty
import net.fabricmc.loom.configuration.ide.idea.IdeaSyncTask
import java.io.FileWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files

plugins {
    id("multiloader-base")
    id("net.fabricmc.fabric-loom")
}

val base = configureInheritingFeature("base", "api:base")
val common = configureInheritingFeature("common", "base", "api:common")
val client = configureInheritingFeature("client", "common", "api:client")
val data = configureInheritingFeature("data", "common", "client", "api:data", publish = true, bundle = listOf("api:data"), depend = listOf("main"))

configureInheritingFeature("main", "base", "common", "client", "api:base", "api:common", "api:client", publish = true, bundle = listOf("base", "common", "client", "api:base", "api:common", "api:client"), excludeClasspathDependencies = true)

// `excludeClasspathDependencies` lets us do this
configurations.named("baseCompileClasspath") { extendsFrom(configurations.compileClasspath) }
configurations.named("baseRuntimeClasspath") { extendsFrom(configurations.runtimeClasspath) }

internal val generated: SourceSet = sourceSets.create("generated") {
    java.setSrcDirs(emptyList<Any>())
}

val baseImplementation by configurations.getting

dependencies {
    minecraft("com.mojang:minecraft:${resolveProperty("vanillaMinecraft")}")    
    // This needs to also be present in "main" so that loom sets up loader and classtweakers properly, as it is hard-coded to "main".
    implementation(baseImplementation("net.fabricmc:fabric-loader:${resolveProperty("fabricLoader")}")!!)
    implementation(baseImplementation("net.fabricmc.fabric-api:fabric-api:${resolveProperty("fabricApi")}")!!)
}

fun generateModFile(name: String = "", dependsOn: Pair<String, String>? = null, withAccessWidener: Boolean = false, withMixins: Boolean = false): TaskProvider<Task> {
    return tasks.register("generate${if (name.isEmpty()) "" else name.replaceFirstChar { it.uppercase() }}ModFile") {
        // Dependencies
        val dependencies = mutableMapOf(
            "java" to resolveProperty("java_version"),
            "minecraft" to "~${resolveProperty("vanillaMinecraft")}",
            "fabricloader" to ">=${resolveProperty("fabricLoader")}",
            "fabric-api" to ">=${resolveProperty("fabricApi")}"
        )
        if (dependsOn != null) {
            dependencies[dependsOn.first] = dependsOn.second
        }

        val modId = "${resolveProperty("mod_id")}${if (name.isEmpty()) "" else "_${name}"}"

        // Base json
        val modsJson: MutableMap<String, Any> = mutableMapOf(
            "schemaVersion" to 1,
            "id" to modId,
            "version" to resolveProperty("mod_version"),
            "name" to "${resolveProperty("mod_name")} (${project.name}${if (name.isEmpty()) "" else "-${name}"})",
            "description" to resolveProperty("mod_description"),
            "authors" to resolveProperty("mod_authors").split(",").map { it.trim() },
            "license" to resolveProperty("mod_license"),
            "environment" to "*",
            "entrypoints" to mapOf<String, List<String>>(),
            "depends" to dependencies
        )

        if (withAccessWidener) {
            modsJson["accessWidener"] = "${modId}.classtweaker"
        }

        val mixins: MutableList<Any> = mutableListOf<Any>()
        if (withMixins) {
            mixins.add("${modId}.mixins.json")
        }
        modsJson["mixins"] = mixins

        val outputDir: File = layout.buildDirectory.asFile.get().resolve("generated/sources/mod_file/${if (name.isEmpty()) "main" else name}")
        val filePath: File = outputDir.resolve("fabric.mod.json")
        Files.createDirectories(filePath.parentFile.toPath())
        FileWriter(filePath, StandardCharsets.UTF_8).use { it.write(JsonOutput.prettyPrint(JsonOutput.toJson(modsJson))) }
        outputs.dir(outputDir)
    }
}

internal val modFile = generateModFile()
internal val dataModFile = generateModFile("data", Pair(resolveProperty("mod_id"), "~${resolveProperty("mod_version")}"))

client.resources {
    srcDir(modFile)
    source(generated.resources)
    exclude("./cache")
}

//internal val dataResources: TaskProvider<ProcessResources> = tasks.register<ProcessResources>("dataTemplates") {
//    val replacements: MutableMap<String, String> = listOf(project, rootProject).asSequence().flatMap { it.extra.properties.entries }.groupBy { it.key }.mapValues { it.value[0].value.toString() }.toMutableMap()
//    replacements["mod_id"] = "${replacements["mod_id"]}_data"
//    replacements["mod_name"] = "${replacements["mod_name"]} (${project.name}-data)"
//
//    inputs.properties(replacements)
//    expand(replacements)
//    from(project.relativePath("src/${data.name}/templates")) {
//        rename {
//            var name = it
//            for (entry in replacements.entries) {
//                name = name.replace(entry.key, entry.value)
//            }
//            name
//        }
//    }
//    val outputDir: File = layout.buildDirectory.asFile.get().resolve("templates/${data.name}")
//    into(outputDir)
//    outputs.dir(outputDir)
//}

//internal val dataMixins: TaskProvider<Task> = tasks.register("dataMixins") {
//    val mixinPath: String = listOf(
//        resolveProperty("mod_group").replace(".", File.separator),
//        resolveProperty("mod_subpackage"),
//        project.name,
//        data.name,
//        "mixin"
//    ).joinToString(File.separator)
//    val mixins: List<String> = data.allSource.filter {
//        it.path.contains(mixinPath) && !it.path.contains("package-info")
//    }.map {
//        it.path.split("$mixinPath${File.separator}").last().substringBeforeLast('.').replace(File.separator, ".")
//    }.toList()
//
//    val mixinsJson: Map<String, Any> = mapOf(
//        "required" to true,
//        "package" to "${resolveProperty("mod_group")}.${resolveProperty("mod_subpackage")}.${project.name}.${data.name}.mixin",
//        "compatibilityLevel" to "JAVA_${resolveProperty("java_version")}",
//        "mixins" to mixins,
//        "injectors" to mapOf(
//            "defaultRequire" to 1
//        )
//    )
//
//    val outputDir: File = layout.buildDirectory.asFile.get().resolve("generated/sources/mixins/${data.name}")
//    val filePath: File = outputDir.resolve("${resolveProperty("mod_id")}_${data.name}.mixins.json")
//    Files.createDirectories(filePath.parentFile.toPath())
//    FileWriter(filePath, StandardCharsets.UTF_8).use { it.write(JsonOutput.prettyPrint(JsonOutput.toJson(mixinsJson))) }
//    outputs.dir(outputDir)
//}

data.resources {
    srcDir(dataModFile)
//    srcDir(dataResources)
//    srcDir(dataMixins)
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

//    accessWidenerPath = layout.buildDirectory.asFile.get().resolve("templates/${data.name}/${resolveProperty("mod_id")}_data.classtweaker")
}

tasks.withType<IdeaSyncTask>().configureEach {
    dependsOn(modFile)
    dependsOn(dataModFile)
//    dependsOn(dataMixins)
//    dependsOn(dataResources)
}

fabricApi.configureDataGeneration {
    client = true
    createRunConfiguration = true
    outputDirectory = generated.resources.srcDirs.first()
}

publication {
    name = "${resolveProperty("mod_name")} (${project.name})"
}
