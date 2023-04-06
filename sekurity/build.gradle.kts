import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.multiplatform)
    `maven-publish`
    signing
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.msignoretto.sekurity"
}

kotlin {
    android()
    ios()
    iosSimulatorArm64()

    sourceSets {

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.junit)
            }
        }
        val iosMain by getting
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

// Publication configuration

val siteUrl = "https://github.com/MarcoSignoretto/SeKurity"
val gitUrl = "https://github.com/MarcoSignoretto/SeKurity.git"

val prop = Properties().apply {
    load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

group = rootProject.group.toString()
version = libs.versions.sekurity.get()

kotlin{
    android {
        publishLibraryVariants("release", "debug")
    }
}

publishing {
    publications {
        withType<MavenPublication> {
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
