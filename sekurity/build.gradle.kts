import java.util.Properties

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
    namespace = "io.github.marcosignoretto.sekurity"
}

dependencies {
    testImplementation(libs.junit)
}

val siteUrl = "https://github.com/MarcoSignoretto/SeKurity"
val gitUrl = "https://github.com/MarcoSignoretto/SeKurity.git"
group = "io.github.marcosignoretto"

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "io.github.marcosignoretto"
            artifactId = "sekurity"
            version = libs.versions.sekurity.get()

            afterEvaluate {
                from(components["release"])
            }

            pom {
                name.set("Sekurity")
                description.set("Utility library for data encryption in Android")
                url.set(siteUrl)
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        id.set("MarcoSignoretto")
                        name.set("Marco Signoretto")
                        email.set("marco.signoretto.dev@gmail.com")
                    }
                }
                scm {
                    connection.set(gitUrl)
                    developerConnection.set(gitUrl)
                    url.set(siteUrl)
                }
            }
        }
    }
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}
