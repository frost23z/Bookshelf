package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.frost23z.bookshelf.ui.addedit.components.core.FormField
import com.frost23z.bookshelf.ui.addedit.components.core.FormFields
import com.frost23z.bookshelf.ui.core.components.IconButton
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun PurchaseSection(
    purchaseFrom: String,
    onPurchaseFromChange: (String) -> Unit,
    purchasePrice: String,
    onPurchasePriceChange: (String) -> Unit,
    purchaseDate: String,
    onPurchaseDateChange: (String) -> Unit
) {
    val isDatePickerVisible = remember { mutableStateOf(false) }

    val formattedDate =
        if (purchaseDate.isNotEmpty()) {
            try {
                Instant
                    .fromEpochMilliseconds(purchaseDate.toLong())
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .date
                    .toString()
            } catch (e: NumberFormatException) {
                ""
            }
        } else {
            ""
        }

    FormFields(
        fields =
            listOf(
                FormField(
                    value = purchaseFrom,
                    onValueChange = onPurchaseFromChange,
                    placeholder = "Purchase From",
                    label = "Purchase From",
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            autoCorrectEnabled = false,
                            imeAction = ImeAction.Next
                        )
                ),
                FormField(
                    value = purchasePrice,
                    onValueChange = onPurchasePriceChange,
                    placeholder = "Purchase Price",
                    label = "Purchase Price",
                    keyboardOptions =
                        KeyboardOptions.Default.copy(
                            autoCorrectEnabled = false,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        )
                ),
                FormField(
                    value = formattedDate,
                    onValueChange = { },
                    placeholder = "YYYY-MM-DD",
                    label = "Purchase Date (YYYY-MM-DD)",
                    trailingIcon = {
                        IconButton(
                            onClick = { isDatePickerVisible.value = true },
                            icon = Icons.Default.DateRange,
                            iconDescription = "Select Date",
                            tooltip = "Select Date"
                        )
                    },
                    readOnly = true
                )
            )
    )
    if (isDatePickerVisible.value) {
        DatePickerModal(
            onDateSelected = { onPurchaseDateChange(it?.toString() ?: "") },
            onDismiss = { isDatePickerVisible.value = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}