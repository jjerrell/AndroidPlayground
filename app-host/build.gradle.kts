@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
}

android {
    namespace = "dev.jjerrell.android.playground.host"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes { release { isMinifyEnabled = false } }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = libs.versions.jvmTarget.get() }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() }
    buildFeatures { compose = true }
}

dependencies {
    val composeBOM = enforcedPlatform(libs.androidx.compose.bom)
    val kotlinBOM = enforcedPlatform(libs.jetbrains.kotlin.bom)
    val koinBOM = enforcedPlatform(libs.koin.bom)
    implementation(composeBOM)
    implementation(kotlinBOM)
    implementation(koinBOM)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(libs.koin.compose)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)

    implementation(libs.timber)

    api(project(":base-android"))
    implementation(project(":feature-about"))
    implementation(project(":feature-welcome"))


    testImplementation(libs.junit.test)

    androidTestImplementation(composeBOM)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.junit.compose.ui.test.junit4)

    debugImplementation(libs.junit.compose.ui.test.tooling)
    debugImplementation(libs.junit.compose.ui.test.manifest)
}
