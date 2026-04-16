import groovy.json.JsonSlurper
import net.ashwork.gradle.multiloader.rootProperty

plugins {
    java
    idea
    id("de.undercouch.download")
    `maven-publish`
}

base.archivesName = "${rootProperty("mod_id")}-${project.name}"
group = rootProperty("mod_group")
version = "${rootProperty("mod_version")}+${rootProperty("vanillaMinecraft")}"

java {
    withSourcesJar()
    toolchain.languageVersion.set(
        JavaLanguageVersion.of(rootProperty("javaVersion"))
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
    src("https://raw.githubusercontent.com/spdx/license-list-data/main/json/details/${rootProperty("mod_license")}.json")
    dest(rootProject.layout.buildDirectory.asFile.get().resolve("licenses/${rootProperty("mod_license")}.json"))
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
        name = "${rootProperty("mod_name")} (${project.name})"
        description = rootProperty("mod_description")
        url = "https://${rootProperty("mod_repository")}"
        licenses {
            license(fromLicenseShortcode(rootProperty("mod_license")))
        }
        issueManagement {
            url = "https://${rootProperty("mod_repository")}/issues"
            system = rootProperty("mod_issue_system")
        }
        developers {
            rootProperty("mod_authors").split(",").forEach {
                developer {
                    name = it.trim()
                    organization = rootProperty("mod_organization")
                }
            }
        }
        scm {
            connection = "scm:git:git://${rootProperty("mod_repository")}.git"
            developerConnection = "scm:git:ssh://${rootProperty("mod_repository")}.git"
            url = "https://${rootProperty("mod_repository")}"
        }
    }
}
