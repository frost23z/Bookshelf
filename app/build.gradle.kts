plugins {
    alias(libs.plugins.android.application)
    alias(kotlinx.plugins.kotlin)
    alias(kotlinx.plugins.compose.compiler)
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
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // jetpack
    implementation(jetpack.core.ktx)
    implementation(jetpack.lifecycle.runtime.ktx)
    implementation(jetpack.activity.compose)

    // compose
    implementation(platform(compose.bom))
    implementation(compose.ui)
    implementation(compose.ui.graphics)
    implementation(compose.ui.tooling.preview)
    implementation(compose.material3)

    // test
    testImplementation(test.junit)
    androidTestImplementation(test.androidx.junit)
    androidTestImplementation(test.espresso.core)

    // ui test
    androidTestImplementation(platform(compose.bom))
    androidTestImplementation(compose.ui.test.junit4)
    debugImplementation(compose.ui.tooling)
    debugImplementation(compose.ui.test.manifest)
}