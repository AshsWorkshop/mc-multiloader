import net.ashwork.gradle.multiloader.configureInheritingFeature
import net.ashwork.gradle.multiloader.publication
import net.ashwork.gradle.multiloader.publishedAccessTransformer
import net.ashwork.gradle.multiloader.resolveProperty

plugins {
    id("multiloader-base")
    id("net.neoforged.moddev")
}

val base = configureInheritingFeature("base")
val common = configureInheritingFeature("common", "base")
val client = configureInheritingFeature("client", "common")
val data = configureInheritingFeature("data", "main", publish = true)

configureInheritingFeature("main", "base", "common", "client", publish = true, bundle = listOf("base", "common", "client"))

// Separate resources
internal val transformers: SourceSet = sourceSets.create("accesstransformers") {
    java.setSrcDirs(listOf<Any>())
}

transformers.resources.files.forEach {
    project.publishedAccessTransformer(it, "data")
}

neoForge {
    // Configure vanilla mode
    neoFormVersion = resolveProperty("vanillaNeoform")

    addModdingDependenciesTo(base)
}

publication {
    name = "${resolveProperty("mod_name")} (${project.name})"
}
