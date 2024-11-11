package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.frost23z.bookshelf.ui.addedit.components.core.FormField
import com.frost23z.bookshelf.ui.addedit.components.core.FormFields
import com.frost23z.bookshelf.ui.core.components.DatePickerModal
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.heplers.formatDateFromTimestamp

@Composable
fun PurchaseSection(
    purchaseFrom: String,
    onPurchaseFromChange: (String) -> Unit,
    purchasePrice: String,
    onPurchasePriceChange: (String) -> Unit,
    purchaseDate: Long,
    onPurchaseDateChange: (Long) -> Unit
) {
    val isDatePickerVisible = rememberSaveable { mutableStateOf(false) }

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
                    value = formatDateFromTimestamp(purchaseDate),
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
            onDateSelected = { onPurchaseDateChange(it ?: 0L) },
            onDismiss = { isDatePickerVisible.value = false }
        )
    }
}