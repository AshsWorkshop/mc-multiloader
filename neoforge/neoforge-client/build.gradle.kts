plugins {
    id("multiloader-neoforge")
}

val rootProperty: (String) -> String by rootProject.extra
val resolveProject: (String, String) -> Project by rootProject.extra

dependencies {
    api(resolveProject("api", "client"))
    api(resolveProject("neoforge", "base"))
}

neoForge {
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

        configureEach {
            systemProperty("forge.logging.markers", "REGISTRIES")
            logLevel = org.slf4j.event.Level.DEBUG
            systemProperty("neoforge.enabledGameTestNamespaces", "${rootProperty("mod_id")}_${project.name.replace("-", "_")}")
        }
    }
}