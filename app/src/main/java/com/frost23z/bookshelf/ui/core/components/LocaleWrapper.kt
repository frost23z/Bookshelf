package com.frost23z.bookshelf.ui.core.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import com.frost23z.bookshelf.data.Languages

val LocalLanguage = compositionLocalOf<Languages> { error("No language provided") }

@Composable
fun rememberLanguage(): Languages = LocalLanguage.current

@Composable
fun LocaleWrapper(
    language: Languages,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalLanguage provides language) {
        content()
    }
}