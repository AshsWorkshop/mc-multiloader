import net.ashwork.gradle.multiloader.*

plugins {
    id("multiloader-publishing")
    `java-platform`
}

// Add gradle properties
apply(from = "properties.gradle.kts")

version = "${resolveProperty("vanillaMinecraft")}.${resolveProperty("mod_platform_version_build")}"

if (!extra.has("mod_subpackage")) {
    extra["mod_subpackage"] = extra["mod_id"]
}

dependencies {
    constraints {
        subprojects.forEach {
            api(it)
        }
    }
}

publishing.publications.create<MavenPublication>("platform") {
    from(components["javaPlatform"])
}
