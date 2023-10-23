@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.kotlin)
}

android {
    namespace = "dev.jjerrell.android.playground.feature.about"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.compileSdk.get().toInt()

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
    kotlinOptions { jvmTarget = libs.versions.jvmTarget.get() }
    composeOptions { kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get() }
    buildFeatures { compose = true }
}

dependencies {
    val composeBOM = enforcedPlatform(libs.androidx.compose.bom)
    implementation(composeBOM)
    implementation(enforcedPlatform(libs.jetbrains.kotlin.bom))
    implementation(enforcedPlatform(libs.koin.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.androidx.compose)

    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.compose.navigation)

    implementation(project(":base-android"))

    testImplementation(libs.junit.test)

    androidTestImplementation(composeBOM)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.junit.compose.ui.test.junit4)

    debugImplementation(libs.junit.compose.ui.test.tooling)
    debugImplementation(libs.junit.compose.ui.test.manifest)
}
