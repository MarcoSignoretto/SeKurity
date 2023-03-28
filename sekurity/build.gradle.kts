import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
    id("signing")
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
    namespace = "com.msignoretto.sekurity"
}

dependencies {
    testImplementation(libs.junit)
}

val siteUrl = "https://github.com/MarcoSignoretto/SeKurity"
val gitUrl = "https://github.com/MarcoSignoretto/SeKurity.git"
group = "com.msignoretto"

val prop = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

publishing {
    publications {

            register<MavenPublication>("release") {
                groupId = "com.msignoretto"
                artifactId = "sekurity"
                version = libs.versions.sekurity.get()

                afterEvaluate{
                    if (project.plugins.findPlugin("com.android.library") != null) {
                        from(components["release"])
                    } else {
                        from(components["java"])
                    }
                }

//                artifact(tasks["javadocJar"]) // TODO consider adding dokka for javadoc

                pom {
                    name.set("Sekurity")
                    description.set("Utility library for data encryption and decryption")
                    url.set(siteUrl)
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://github.com/MarcoSignoretto/SeKurity/blob/master/LICENSE")
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

        signing {
            useInMemoryPgpKeys(
                prop["signing.keyId"] as String,
                prop["signing.key"] as String,
                prop["signing.password"] as String,
            )
            sign(publishing.publications)
        }
    }
}
