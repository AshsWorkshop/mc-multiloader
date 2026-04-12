plugins {
    id("multiloader-neoforge")
}

val resolveProject: (String, String) -> Project by rootProject.extra

dependencies {
    api(resolveProject("api", "common"))
    api(resolveProject("neoforge", "base"))
}
