package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.addedit.components.core.BorderedContainer
import com.frost23z.bookshelf.ui.addedit.components.core.FormField
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.constants.LargeIcon

@Composable
fun StatusSection(
    totalPages: Long,
    readPages: Long,
    onStatusChange: (String) -> Unit,
    onReadPagesChange: (Long) -> Unit,
) {
    var status by rememberSaveable { mutableStateOf("Unread") }
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    var sliderValue by rememberSaveable { mutableFloatStateOf(readPages.toFloat()) }

    val statusOptions = listOf("Unread", "Reading", "Read")

    val onStatusSelected = { newStatus: String ->
        status = newStatus
        onStatusChange(newStatus)

        sliderValue =
            when (newStatus) {
                "Unread" -> 0f
                "Read" -> totalPages.toFloat()
                else -> sliderValue
            }
        onReadPagesChange(sliderValue.toLong())
    }

    BorderedContainer(content = {
        FormField(
            FormField(
                value = status,
                onValueChange = {},
                placeholder = "Select your status...",
                label = "Status",
                enabled = false,
                readOnly = true,
                trailingIcon = {
                    IconButton(
                        onClick = { showDropdown = true },
                        icon = Icons.Default.ModeEdit,
                        iconDescription = "Edit Reading Status",
                        tooltip = "Reading Status"
                    )
                }
            )
        )

        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false },
            offset = DpOffset(x = 40.dp, y = (-70).dp)
        ) {
            statusOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onStatusSelected(option)
                        showDropdown = false
                    }
                )
            }
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = sliderValue.toInt().toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(LargeIcon),
            )

            Slider(
                value = sliderValue,
                onValueChange = {
                    sliderValue = it
                    onReadPagesChange(sliderValue.toLong())

                    status =
                        when {
                            sliderValue == totalPages.toFloat() -> "Read"
                            sliderValue == 0f -> "Unread"
                            else -> "Reading"
                        }
                    onStatusChange(status)
                },
                steps =
                    when (totalPages) {
                        in 0..1 -> 0
                        in 2..100 -> totalPages.toInt() - 1
                        else -> 100
                    },
                valueRange = 0f..totalPages.toFloat(),
                modifier = Modifier.weight(1f)
            )

            Text(
                text = totalPages.toString(),
                modifier = Modifier.width(LargeIcon),
                textAlign = TextAlign.Center
            )
        }
    })
}

@Preview
@Composable
private fun StatusSectionPreview() {
    StatusSection(totalPages = 100, readPages = 50, onStatusChange = {}, onReadPagesChange = {})
}
