package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material.icons.outlined.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.frost23z.bookshelf.ui.addedit.components.core.FormField
import com.frost23z.bookshelf.ui.addedit.components.core.FormFields
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.constants.LargeIcon

@Composable
fun TitleSection(
    titlePrefix: String,
    onTitlePrefixChange: (String) -> Unit,
    title: String,
    onTitleChange: (String) -> Unit,
    titleSuffix: String,
    onTitleSuffixChange: (String) -> Unit,
) {
    var detailedTitle by rememberSaveable { mutableStateOf(false) }
    FormFields(
        fields =
            listOf(
                FormField(
                    value = titlePrefix,
                    onValueChange = onTitlePrefixChange,
                    placeholder = "Title Prefix",
                    label = "Title Prefix",
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            autoCorrectEnabled = false,
                            imeAction = ImeAction.Next
                        ),
                    leadingIcon = {
                        if (detailedTitle) {
                            Icon(
                                icon = Icons.Outlined.Title,
                                iconDescription = "Title",
                                containerSize = LargeIcon
                            )
                        }
                    },
                    trailingIcon = {
                        if (detailedTitle) {
                            IconButton(
                                icon = Icons.Outlined.ExpandLess,
                                onClick = { detailedTitle = !detailedTitle },
                                iconDescription = "Navigate up",
                                tooltip = "Back"
                            )
                        }
                    }
                ),
                FormField(
                    value = title,
                    onValueChange = onTitleChange,
                    placeholder = "Title",
                    label = "Title",
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            autoCorrectEnabled = false,
                            imeAction = ImeAction.Next
                        ),
                    leadingIcon =
                        if (detailedTitle) {
                            null
                        } else {
                            {
                                Icon(
                                    icon = Icons.Outlined.Title,
                                    iconDescription = "Title",
                                    containerSize = LargeIcon
                                )
                            }
                        },
                    trailingIcon =
                        if (detailedTitle) {
                            null
                        } else {
                            {
                                IconButton(
                                    onClick = { detailedTitle = !detailedTitle },
                                    icon = Icons.Outlined.ExpandMore,
                                    iconDescription = "Navigate up",
                                    tooltip = "Back"
                                )
                            }
                        }
                ),
                FormField(
                    value = titleSuffix,
                    onValueChange = onTitleSuffixChange,
                    placeholder = "Title Suffix",
                    label = "Title Suffix",
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            autoCorrectEnabled = false,
                            imeAction = ImeAction.Next
                        )
                )
            ),
        fieldsVisibility = listOf(detailedTitle, true, detailedTitle),
    )
}

@Preview
@Composable
private fun Title() {
    var titlePrefix by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var titleSuffix by rememberSaveable { mutableStateOf("") }
    TitleSection(
        titlePrefix = titlePrefix,
        onTitlePrefixChange = { titlePrefix = it },
        title = title,
        onTitleChange = { title = it },
        titleSuffix = titleSuffix,
        onTitleSuffixChange = { titleSuffix = it },
    )
}