import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.provideDelegate
import net.ashwork.gradle.multiloader.*

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
    neoFormVersion = rootProperty("vanillaNeoform")
}
