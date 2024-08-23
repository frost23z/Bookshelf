// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(kotlinx.plugins.kotlin) apply false
    alias(kotlinx.plugins.compose.compiler) apply false
    alias(libs.plugins.sqldelight) apply false
}