plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.kotlin.compose)
	alias(libs.plugins.spotless)
	alias(libs.plugins.sqldelight)
}

android {
	namespace = "com.frost23z.bookshelf"
	compileSdk = 35

	defaultConfig {
		applicationId = "com.frost23z.bookshelf"
		minSdk = 24
		targetSdk = 35
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

	implementation(jetpack.bundles.core)
	implementation(platform(jetpack.compose.bom))
	implementation(jetpack.bundles.compose)

	implementation(libs.bundles.sqldelight)

	testImplementation(testdebug.junit)
	androidTestImplementation(testdebug.bundles.androidTest)
	androidTestImplementation(platform(jetpack.compose.bom))
	debugImplementation(testdebug.bundles.debug)
}

sqldelight {
	databases {
		create("AppDatabase") {
			packageName.set("com.frost23z.bookshelf.data")
			dialect(libs.sqldelight.dialects.sql)
			// Additional configuration options if required
			// https://sqldelight.github.io/sqldelight/2.0.2/android_sqlite/gradle/
		}
	}
}

spotless {
	kotlin {
		target("**/*.kt", "**/*.kts")
		targetExclude("**/build/**/*.kt")
		ktlint(
			libs.ktlint.core
				.get()
				.version
		).editorConfigOverride(
			mapOf(
				// https://pinterest.github.io/ktlint/latest/rules/code-styles/
				"ktlint_code_style" to "ktlint_official",
				// https://pinterest.github.io/ktlint/latest/rules/standard
				"ktlint_function_naming_ignore_when_annotated_with" to "Composable",
				"ktlint_standard_binary-expression-wrapping" to "disabled",
				"ktlint_standard_class-signature" to "disabled",
				"ktlint_standard_final-newline" to "disabled",
				"ktlint_function_signature_body_expression_wrapping" to "default",
				"ktlint_standard_no-unit-return" to "disabled",
				"ktlint_standard_trailing-comma-on-call-site" to "disabled",
				"ktlint_standard_trailing-comma-on-declaration-site" to "disabled",
				"ktlint_standard_multiline-expression-wrapping" to "disabled",
				"ktlint_standard_property-wrapping" to "disabled"
			)
		)
		trimTrailingWhitespace()
		leadingSpacesToTabs()
	}
	format("xml") {
		target("**/*.xml")
		trimTrailingWhitespace()
	}
	sql {
		target("**/*.sq")
		dbeaver()
	}
}