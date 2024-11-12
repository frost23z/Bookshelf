package com.frost23z.bookshelf.ui.core.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

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