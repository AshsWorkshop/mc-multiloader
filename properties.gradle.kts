// Common properties
val javaVersion by extra("25")

// Vanilla properties
val vanillaMinecraft by extra("26.1.2")
// - https://projects.neoforged.net/neoforged/neoform
val vanillaNeoform by extra("${vanillaMinecraft}-1")

// NeoForge properties
// - https://projects.neoforged.net/neoforged/moddevgradle
val neoforgeGradle by extra("2.0.141")
// - https://projects.neoforged.net/neoforged/neoforge
val neoforgeApi by extra("${vanillaMinecraft}.7-beta")

// Fabric properties
// - https://fabricmc.net/develop/
val fabricLoader by extra("0.19.1")
val fabricLoom by extra("1.16-SNAPSHOT")
val fabricApi by extra("0.145.4+${vanillaMinecraft}")
