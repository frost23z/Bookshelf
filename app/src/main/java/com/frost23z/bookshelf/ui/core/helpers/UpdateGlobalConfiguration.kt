package com.frost23z.bookshelf.ui.core.helpers

import android.content.Context
import android.content.res.Configuration

fun Context.updateGlobalConfiguration(isDarkTheme: Boolean): Context {
    val configuration = Configuration(this.resources.configuration)
    configuration.uiMode = (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK.inv()) or
        if (isDarkTheme) {
            Configuration.UI_MODE_NIGHT_YES
        } else {
            Configuration.UI_MODE_NIGHT_NO
        }

    return this.createConfigurationContext(configuration)
}