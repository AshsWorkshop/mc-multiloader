plugins {
    id("multiloader-fabric")
}

val resolveProject: (String, String) -> Project by rootProject.extra

dependencies {
    api(resolveProject("api", "data"))
    api(resolveProject("fabric", "base"))
}
