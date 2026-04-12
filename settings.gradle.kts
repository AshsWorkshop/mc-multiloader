plugins {
    // https://plugins.gradle.org/plugin/org.gradle.toolchains.foojay-resolver-convention
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "multiloader"

// Include all projects
listOf(
    "api",
    "neoforge",
    "fabric"
).map {
    rootProject.projectDir.resolve(it)
}.flatMap {
    it.listFiles { it.isDirectory && it.resolve("build.gradle.kts").exists() }.toList()
}.forEach {
    include(it.toRelativeString(rootProject.projectDir).replace(File.separator, ":"))
}
