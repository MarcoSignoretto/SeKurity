import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.1")
        classpath(libs.plugin.kotlin)
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.5")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.7.3")
    }
}

val min_sdk = 23
val target_sdk = 33
val compile_sdk = 33
val javaVersion = JavaVersion.VERSION_1_8



allprojects {
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
