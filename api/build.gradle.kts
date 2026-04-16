import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.provideDelegate
import net.ashwork.gradle.multiloader.*
import java.util.Locale

plugins {
    id("multiloader-base")
    id("net.neoforged.moddev")
}

// Create source sets
internal val base: SourceSet = sourceSets.createFrom("base", sourceSets["main"])
internal val common: SourceSet = sourceSets.createFrom("common", base, base)
internal val client: SourceSet = sourceSets.createFrom("client", common, common)
internal val data: SourceSet = sourceSets.createFrom("data", base, base)

neoForge {
    // Configure vanilla mode
    neoFormVersion = resolveProperty("vanillaNeoform")
}

afterEvaluate {
    var jar = publishSourceSets(
        project.name, listOf(sourceSets["base"], common, client),
        project.base.archivesName.get()
    )
    publishSourceSets(
        "${project.name}Data", listOf(data),
        "${project.base.archivesName.get()}-data"
    ) {
        name = "${resolveProperty("mod_name")} (${project.name}-data)"

        // Need to manually resolve dependency due to source set shenanigans
        compileDependency(jar)
    }
}
