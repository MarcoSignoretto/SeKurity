import org.gradle.api.publish.maven.MavenPublication
import java.util.Properties


plugins {
    id("com.android.library")
    id("kotlin-android")
    id("maven-publish")
}

val compile_sdk = 33
val min_sdk = 23
val target_sdk = 33
val junit_version = "4.13.2"
val version = "1.1.0"

android {
    compileSdk = compile_sdk
    defaultConfig {
        minSdk = min_sdk
        targetSdk = target_sdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    namespace = "io.github.marcosignoretto.sekurity"
}

dependencies {
    testImplementation("junit:junit:$junit_version")
}

val siteUrl = "https://github.com/MarcoSignoretto/SeKurity"
val gitUrl = "https://github.com/MarcoSignoretto/SeKurity.git"
group = "io.github.marcosignoretto"

//val publications = publishing {
//    publications {
//        create<MavenPublication>("maven") {
//            groupId = "io.github.marcosignoretto"
//            artifactId = "sekurity"
//            version = version
//            from(components["release"])
//
//            artifact("$buildDir/outputs/aar/sekurity-release.aar")
//
//            pom {
//                name.set("io.github.marcosignoretto:sekurity")
//                description.set("Utility library for data encryption in Android")
//                url.set(siteUrl)
//                licenses {
//                    license {
//                        name.set("MIT License")
//                        url.set("http://www.opensource.org/licenses/mit-license.php")
//                    }
//                }
//                developers {
//                    developer {
//                        id.set("MarcoSignoretto")
//                        name.set("Marco Signoretto")
//                        email.set("marco.signoretto.dev@gmail.com")
//                    }
//                }
//                scm {
//                    connection.set(gitUrl)
//                    developerConnection.set(gitUrl)
//                    url.set(siteUrl)
//                }
//            }
//        }
//    }
//}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

// bintray {
//     user = properties.getProperty("bintray.user")
//     key = properties.getProperty("bintray.apikey")

//     configurations = ["archives"]
//     publish = true
//     pkg {
//         repo = "maven"
//         name = "io.github.marcosignoretto:sekurity"
//         websiteUrl = siteUrl
//         vcsUrl = gitUrl
//         licenses = listOf("MIT")
//         version {
//             gpg {
//                 sign = true
//                 passphrase = properties.getProperty("bintray.gpg.password")
//             }
//         }
//     }
// }

// task sourcesJar(type: Jar) {
//     from(android.sourceSets.main.java.srcDirs)
//     classifier = "sources"
// }

// task javadoc(type: Javadoc) {
//     excludes("**/*.kt")
//     source(android.sourceSets.main.java.srcDirs)
//     classpath += project.files(android.getBootClasspath().join(File.pathSeparator))
// }

// task javadocJar(type: Jar, dependsOn = "javadoc") {
//     classifier = "javadoc"
//     from(tasks["javadoc"].destinationDir)
// }

// artifacts {
//     archives(javadocJar)
//     archives(sourcesJar)
// }
