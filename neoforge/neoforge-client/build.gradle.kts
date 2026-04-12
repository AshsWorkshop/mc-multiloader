plugins {
    id("multiloader-neoforge")
}

val resolveProject: (String, String) -> Project by rootProject.extra

dependencies {
    api(resolveProject("api", "client"))
    api(resolveProject("neoforge", "base"))
}
