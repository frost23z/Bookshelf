package com.frost23z.bookshelf.ui.core.util

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.max

data class TextStyles(
    val textFieldLabelStyle: TextStyle,
    val textFieldStyle: TextStyle
)

@Composable
fun getTextStyles(): TextStyles {
    return TextStyles(
        textFieldLabelStyle = MaterialTheme.typography.labelLarge,
        textFieldStyle = MaterialTheme.typography.bodyLarge
    )
}

val GetTextStyle: TextStyles
    @Composable
    get() = getTextStyles()

@Composable
fun maxCutoutPadding(): Dp {
    val cutoutPadding = WindowInsets.displayCutout.asPaddingValues()
    val layoutDirection = LocalLayoutDirection.current
    val startPadding = cutoutPadding.calculateStartPadding(layoutDirection)
    val endPadding = cutoutPadding.calculateEndPadding(layoutDirection)
    return max(startPadding, endPadding)
}