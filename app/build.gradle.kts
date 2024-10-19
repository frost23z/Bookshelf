plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.frost23z.bookshelf"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.frost23z.bookshelf"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(jetpack.bundles.jetpack)
    implementation(platform(jetpack.compose.bom))
    implementation(jetpack.bundles.compose)

    testImplementation(libs.junit)
    androidTestImplementation(jetpack.bundles.androidTest)
    androidTestImplementation(platform(jetpack.compose.bom))
    debugImplementation(jetpack.bundles.debug)
}