package com.frost23z.bookshelf.ui.theme.colorscheme

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme

internal class DynamicColorScheme(
    context: Context
) : ColorSchemeController() {
    @RequiresApi(Build.VERSION_CODES.S)
    override val darkScheme = dynamicDarkColorScheme(context)

    @RequiresApi(Build.VERSION_CODES.S)
    override val lightScheme = dynamicLightColorScheme(context)
}