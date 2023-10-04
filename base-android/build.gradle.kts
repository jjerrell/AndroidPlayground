@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
}

android {
    namespace = "dev.jjerrell.android.playground.base.android"
    compileSdk = 33

    defaultConfig {
        minSdk = 29

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {}
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.3" }
    buildFeatures { compose = true }
}

dependencies {
    val composeBOM = enforcedPlatform(libs.androidx.compose.bom)
    val kotlinBOM = enforcedPlatform(libs.jetbrains.kotlin.bom)
    implementation(composeBOM)
    implementation(kotlinBOM)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.androidx.compose)

    implementation(libs.timber)

    testImplementation(libs.junit.test)

    androidTestImplementation(composeBOM)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.junit.compose.ui.test.junit4)

    debugImplementation(libs.junit.compose.ui.test.tooling)
    debugImplementation(libs.junit.compose.ui.test.manifest)
}
