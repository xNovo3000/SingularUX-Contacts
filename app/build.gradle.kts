plugins {
    alias(libs.plugins.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "org.singularux.contacts"
    compileSdk = 35

    defaultConfig {
        applicationId = "org.singularux.contacts"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

}

dependencies {
    // AndroidX
    implementation(libs.androidx.core)
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Material 3
    implementation(libs.google.material)
    // JDK 11 desugaring
    coreLibraryDesugaring(libs.jdk.desugaring)
}

kapt {
    correctErrorTypes = true
}