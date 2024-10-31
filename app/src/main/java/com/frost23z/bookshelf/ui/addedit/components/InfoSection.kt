package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Publish
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.frost23z.bookshelf.ui.addedit.components.core.FormField
import com.frost23z.bookshelf.ui.addedit.components.core.FormFields
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.constants.LargeIcon

@Composable
fun InfoSection(
    publisher: String,
    onPublisherChange: (String) -> Unit,
    language: String,
    onLanguageChange: (String) -> Unit,
    pages: String,
    onPagesChange: (String) -> Unit,
    format: String,
    onFormatChange: (String) -> Unit,
) {
    FormFields(
        fields =
            listOf(
                FormField(
                    value = publisher,
                    onValueChange = onPublisherChange,
                    placeholder = "Publisher",
                    label = "Publisher",
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            autoCorrectEnabled = false,
                            imeAction = ImeAction.Next
                        ),
                    leadingIcon = {
                        Icon(
                            icon = Icons.Default.Publish,
                            iconDescription = "Publisher",
                            containerSize = LargeIcon
                        )
                    }
                ),
                FormField(
                    value = language,
                    onValueChange = onLanguageChange,
                    placeholder = "Language",
                    label = "Language",
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            autoCorrectEnabled = false,
                            imeAction = ImeAction.Next
                        ),
                    leadingIcon = {
                        Icon(
                            icon = Icons.Default.Language,
                            iconDescription = "Language",
                            containerSize = LargeIcon
                        )
                    }
                ),
                FormField(
                    value = pages,
                    onValueChange = { it -> if (it.length <= 4 && it.isDigitsOnly()) onPagesChange(it) },
                    placeholder = "Pages",
                    label = "Pages",
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                    leadingIcon = {
                        Icon(
                            icon = Icons.Default.AutoStories,
                            iconDescription = "Pages",
                            containerSize = LargeIcon
                        )
                    }
                ),
                FormField(
                    value = format,
                    onValueChange = onFormatChange,
                    placeholder = "Format",
                    label = "Format",
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            autoCorrectEnabled = false,
                            imeAction = ImeAction.Next
                        ),
                    leadingIcon = {
                        Icon(
                            icon = Icons.Default.CollectionsBookmark,
                            iconDescription = "Format",
                            containerSize = LargeIcon
                        )
                    }
                )
            )
    )
}