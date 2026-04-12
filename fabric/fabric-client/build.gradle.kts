plugins {
    id("multiloader-fabric")
}

val resolveProject: (String, String) -> Project by rootProject.extra

dependencies {
    api(resolveProject("api", "client"))
    api(resolveProject("fabric", "base"))
}
