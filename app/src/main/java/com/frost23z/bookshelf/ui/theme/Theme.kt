package com.frost23z.bookshelf.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
	primary = Color(0xFF97D945),
	onPrimary = Color(0xFF1F3700),
	primaryContainer = Color(0xFF64A104),
	onPrimaryContainer = Color(0xFF192F00),
	secondary = Color(0xFFB1D188),
	onSecondary = Color(0xFF1F3700),
	secondaryContainer = Color(0xFF365016),
	onSecondaryContainer = Color(0xFFA3C37B),
	tertiary = Color(0xFF5DDCB0),
	onTertiary = Color(0xFF003828),
	tertiaryContainer = Color(0xFF05A47C),
	onTertiaryContainer = Color(0xFF002F21),
	error = Color(0xFFFFB4AB),
	onError = Color(0xFF690005),
	errorContainer = Color(0xFF93000A),
	onErrorContainer = Color(0xFFFFDAD6),
	background = Color(0xFF11150B),
	onBackground = Color(0xFFE0E4D4),
	surface = Color(0xFF11150B),
	onSurface = Color(0xFFE0E4D4),
	surfaceVariant = Color(0xFF424937),
	onSurfaceVariant = Color(0xFFC2CAB2),
	outline = Color(0xFF8C947E),
	outlineVariant = Color(0xFF424937),
	scrim = Color(0xFF000000),
	inverseSurface = Color(0xFFE0E4D4),
	inverseOnSurface = Color(0xFF2E3227),
	inversePrimary = Color(0xFF3F6900),
	surfaceDim = Color(0xFF11150B),
	surfaceBright = Color(0xFF363B2F),
	surfaceContainerLowest = Color(0xFF0B0F07),
	surfaceContainerLow = Color(0xFF191D13),
	surfaceContainer = Color(0xFF1D2117),
	surfaceContainerHigh = Color(0xFF272B21),
	surfaceContainerHighest = Color(0xFF32362B)
)

private val LightColorScheme = lightColorScheme(
	primary = Color(0xFF3E6700),
	onPrimary = Color(0xFFFFFFFF),
	primaryContainer = Color(0xFF4F8200),
	onPrimaryContainer = Color(0xFFF9FFEA),
	secondary = Color(0xFF4B662A),
	onSecondary = Color(0xFFFFFFFF),
	secondaryContainer = Color(0xFFCCEEA1),
	onSecondaryContainer = Color(0xFF516D2F),
	tertiary = Color(0xFF00694E),
	onTertiary = Color(0xFFFFFFFF),
	tertiaryContainer = Color(0xFF008564),
	onTertiaryContainer = Color(0xFFF5FFF8),
	error = Color(0xFFBA1A1A),
	onError = Color(0xFFFFFFFF),
	errorContainer = Color(0xFFFFDAD6),
	onErrorContainer = Color(0xFF93000A),
	background = Color(0xFFF7FBEA),
	onBackground = Color(0xFF191D13),
	surface = Color(0xFFF7FBEA),
	onSurface = Color(0xFF191D13),
	surfaceVariant = Color(0xFFDEE6CD),
	onSurfaceVariant = Color(0xFF424937),
	outline = Color(0xFF727A66),
	outlineVariant = Color(0xFFC2CAB2),
	scrim = Color(0xFF000000),
	inverseSurface = Color(0xFF2E3227),
	inverseOnSurface = Color(0xFFEFF3E2),
	inversePrimary = Color(0xFF97D945),
	surfaceDim = Color(0xFFD8DCCC),
	surfaceBright = Color(0xFFF7FBEA),
	surfaceContainerLowest = Color(0xFFFFFFFF),
	surfaceContainerLow = Color(0xFFF2F5E5),
	surfaceContainer = Color(0xFFECF0DF),
	surfaceContainerHigh = Color(0xFFE6EADA),
	surfaceContainerHighest = Color(0xFFE0E4D4)
)

@Composable
fun BookshelfTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	// Dynamic color is available on Android 12+
	dynamicColor: Boolean = true,
	content: @Composable () -> Unit
) {
	val colorScheme = when {
		dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
			val context = LocalContext.current
			if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
		}

		darkTheme -> DarkColorScheme
		else -> LightColorScheme
	}

	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}