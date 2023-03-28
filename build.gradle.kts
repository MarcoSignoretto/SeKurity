import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath(libs.plugin.kotlin)
    }
}

plugins{
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

val prop = java.util.Properties().apply {
    load(java.io.FileInputStream(File(rootProject.rootDir, "local.properties")))
}

nexusPublishing{
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(prop["ossrhUsername"] as String)
            password.set(prop["ossrhPassword"] as String)
            stagingProfileId.set(prop["sonatypeStagingProfileId"] as String)
        }
    }
}

val min_sdk = 23
val target_sdk = 33
val compile_sdk = 33
val javaVersion = JavaVersion.VERSION_1_8



allprojects {
    group = "com.msignoretto.sekurity"
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    plugins.withType<com.android.build.gradle.BasePlugin>().configureEach {
        val android =
            (project.extensions.getByName("android") as com.android.build.gradle.BaseExtension)
        android.apply {
            compileSdkVersion(compile_sdk)
            defaultConfig {
                multiDexEnabled = true
                minSdk = min_sdk
                targetSdk = target_sdk
            }
            compileOptions {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }
        }
    }
    tasks.withType<KotlinJvmCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}
