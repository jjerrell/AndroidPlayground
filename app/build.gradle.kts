@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "dev.jjerrell.android.playground"
    compileSdk = 34

    defaultConfig {
        applicationId = "dev.jjerrell.android.playground"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions { jvmTarget = "17" }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.3" }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    buildTypes {
        debug {
            //            buildConfigField "String", "URL_SEARCH",
            // "\"https://dev-search.example.com\""
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            //            buildConfigField "String", "URL_SEARCH", "\"https://search.example.com\""
        }
    }
    packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

dependencies {
    val composeBOM = enforcedPlatform(libs.androidx.compose.bom)

    implementation(libs.androidx.core.ktx)

    implementation(enforcedPlatform(libs.jetbrains.kotlin.bom))
    implementation(libs.bundles.androidx.ui)

    implementation(composeBOM)
    implementation(libs.bundles.androidx.compose)
    implementation(libs.androidx.navigation.compose)

    implementation(enforcedPlatform(libs.koin.bom))
    implementation(libs.bundles.koin.androidx.ui)

    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    implementation(project(":base-android"))
    implementation(project(":demo"))

    implementation(project(":feature-about"))

    implementation(libs.timber)

    testImplementation(libs.junit.test)

    androidTestImplementation(composeBOM)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.junit.compose.ui.test.junit4)

    debugImplementation(libs.junit.compose.ui.test.tooling)
    debugImplementation(libs.junit.compose.ui.test.manifest)
}
