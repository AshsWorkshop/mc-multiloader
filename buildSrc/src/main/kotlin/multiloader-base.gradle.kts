import groovy.json.JsonSlurper
import net.ashwork.gradle.multiloader.resolveProperty

plugins {
    java
    idea
    id("de.undercouch.download")
    `maven-publish`
}

base.archivesName = "${resolveProperty("mod_id")}-${project.name}"
group = resolveProperty("mod_group")
version = "${resolveProperty("mod_version")}+${resolveProperty("vanillaMinecraft")}"

java {
    withSourcesJar()
    toolchain.languageVersion.set(
        JavaLanguageVersion.of(resolveProperty("javaVersion"))
    )
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

idea.module {
    isDownloadJavadoc = true
    isDownloadSources = true
}

download.run {
    src("https://raw.githubusercontent.com/spdx/license-list-data/main/json/details/${resolveProperty("mod_license")}.json")
    dest(rootProject.layout.buildDirectory.asFile.get().resolve("licenses/${resolveProperty("mod_license")}.json"))
    onlyIfModified(true)
}

fun fromLicenseShortcode(shortcode: String): Action<MavenPomLicense> {
    val license: Map<String, Any> = JsonSlurper().parse(rootProject.layout.buildDirectory.asFile.get().resolve("licenses/${shortcode}.json")) as Map<String, Any>
    return object : Action<MavenPomLicense> {
        override fun execute(l: MavenPomLicense) {
            l.name = license["licenseId"] as String
            l.url = (license["seeAlso"] as List<String>)[0]
            if ("licenseComments" in license) {
                l.comments = license["licenseComments"] as String
            }
            l.distribution = "repo"
        }
    }
}

publishing.publications.withType<MavenPublication>().configureEach {
    pom {
        name = "${resolveProperty("mod_name")} (${project.name})"
        description = resolveProperty("mod_description")
        url = "https://${resolveProperty("mod_repository")}"
        licenses {
            license(fromLicenseShortcode(resolveProperty("mod_license")))
        }
        issueManagement {
            url = "https://${resolveProperty("mod_repository")}/issues"
            system = resolveProperty("mod_issue_system")
        }
        developers {
            resolveProperty("mod_authors").split(",").forEach {
                developer {
                    name = it.trim()
                    organization = resolveProperty("mod_organization")
                }
            }
        }
        scm {
            connection = "scm:git:git://${resolveProperty("mod_repository")}.git"
            developerConnection = "scm:git:ssh://${resolveProperty("mod_repository")}.git"
            url = "https://${resolveProperty("mod_repository")}"
        }
    }
}
