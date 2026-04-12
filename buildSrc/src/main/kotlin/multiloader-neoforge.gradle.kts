plugins {
    id("multiloader-base")
    id("net.neoforged.moddev")
}

val rootProperty: (String) -> String by rootProject.extra

neoForge {
    version = rootProperty("neoforgeApi")
}
