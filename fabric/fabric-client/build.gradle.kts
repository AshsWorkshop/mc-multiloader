plugins {
    id("multiloader-fabric")
}

val resolveProject: (String, String) -> Project by rootProject.extra

dependencies {
    api(resolveProject("api", "client"))
    api(resolveProject("fabric", "base"))
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
        }
    }
}
