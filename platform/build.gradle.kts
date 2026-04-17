import net.ashwork.gradle.multiloader.*

plugins {
    id("multiloader-publishing")
    `java-platform`
}

publishing.publications.create<MavenPublication>("platform") {
    group = resolveProperty("mod_group")
    artifactId = resolveProperty("mod_id")
    version = "${resolveProperty("vanillaMinecraft")}.${resolveProperty("mod_version_build")}"

    pom {
        dependencyManagement {
            rootProject.subprojects.filter { it.name != project.name }.flatMap {
                it.extensions.getByType<PublishingExtension>().publications.withType<MavenPublication>()
            }.forEach { dependency(it.groupId, it.artifactId, it.version) }
        }
    }
}
