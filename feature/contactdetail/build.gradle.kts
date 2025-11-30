import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "org.singularux.contacts.feature.contactdetail"
    compileSdk {
        version = release(36)
    }
    defaultConfig {
        minSdk = 26
        consumerProguardFiles("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
}

dependencies {
    // Project
    api(project(":core:ui"))
    api(project(":data:contacts"))
    // AndroidX
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity)
    // Coil
    implementation(libs.coil)
    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.animation)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    // Compose - Preview
    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)
    // Compose - Material 3
    implementation(libs.compose.material3)
    // Compose - Accompanist
    implementation(libs.compose.accompanist.permissions)
    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // Lifecycle
    implementation(libs.lifecycle.viewmodel)
}