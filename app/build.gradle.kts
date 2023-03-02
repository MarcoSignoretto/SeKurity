plugins {
    id("com.android.application")
    id("kotlin-android")
}

val compile_sdk = 33
val min_sdk = 23
val target_sdk = 33
val junit_version = "4.13.2"


android {
    compileSdk = compile_sdk
    defaultConfig {
        applicationId = "io.github.marcosignoretto.sekurity.app"
        minSdk = min_sdk
        targetSdk = target_sdk
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    namespace = "io.github.marcosignoretto.sekurity.app"
}

dependencies {

    implementation(project(":sekurity"))

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:$junit_version")
    androidTestImplementation("androidx.test:runner:1.5.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
