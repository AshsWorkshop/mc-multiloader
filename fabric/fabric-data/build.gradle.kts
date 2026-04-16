plugins {
    id("multiloader-fabric")
}

val resolveProject: (String, String) -> Project by rootProject.extra

internal val generated: SourceSet = sourceSets.create("generated") {
    java.setSrcDirs(emptyList<Any>())
}

sourceSets["main"].resources {
    source(generated.resources)
    exclude("./cache")
}

dependencies {
    api(resolveProject("api", "data"))
    api(resolveProject("fabric", "base"))
}

fabricApi.configureDataGeneration {
    client = true
    createRunConfiguration = true
    outputDirectory = generated.resources.srcDirs.first()
}
