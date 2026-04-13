plugins {
    id("multiloader-neoforge")
}

val rootProperty: (String) -> String by rootProject.extra
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
    api(resolveProject("neoforge", "base"))
}

neoForge {
    runs {
        create("clientData") {
            clientData()
            programArguments.addAll("--mod", "${rootProperty("mod_id")}_${project.name.replace("-", "_")}", "--all", "--output", generated.resources.srcDirs.first().absolutePath)
            sourceSets["main"].resources.srcDirs.forEach { programArguments.addAll("--existing", it.absolutePath) }
        }

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
        }
    }
}
