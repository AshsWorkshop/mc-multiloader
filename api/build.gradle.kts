import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.provideDelegate

plugins {
    id("multiloader-base")
    id("net.neoforged.moddev")
}

val rootProperty: (String) -> String by rootProject.extra

fun createSourceSet(name: String, withClasspath: SourceSet, withOutput: SourceSet? = null): SourceSet {
    return sourceSets.create(name) {
        compileClasspath += withClasspath.compileClasspath
        runtimeClasspath += withClasspath.runtimeClasspath
        if (withOutput != null) {
            compileClasspath += withOutput.output
            runtimeClasspath += withOutput.output
        }
    }
}

// Create source sets
internal val base: SourceSet = createSourceSet("base", sourceSets["main"])
internal val common: SourceSet = createSourceSet("common", base, base)
internal val client: SourceSet = createSourceSet("client", common, common)
internal val data: SourceSet = createSourceSet("data", base, base)

neoForge {
    // Configure vanilla mode
    neoFormVersion = rootProperty("vanillaNeoform")
}
