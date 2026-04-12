plugins {
    id("multiloader-fabric")
}

val resolveProject: (String, String) -> Project by rootProject.extra

dependencies {
    api(resolveProject("api", "common"))
    api(resolveProject("fabric", "base"))
}
