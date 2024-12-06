
package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.frost23z.bookshelf.ui.addedit.components.core.FormField
import com.frost23z.bookshelf.ui.addedit.components.core.FormFields
import com.frost23z.bookshelf.ui.core.components.DatePickerModal
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.helpers.formatDateFromTimestamp

@Composable
fun LentSection(
    lentTo: String,
    onLentToChange: (String) -> Unit,
    lentDate: Long,
    onLentDateChange: (Long) -> Unit,
    lentReturned: Long,
    onLentReturnedChange: (Long) -> Unit
) {
    var isLentDatePickerVisible by rememberSaveable { mutableStateOf(false) }
    var isReturnDatePickerVisible by rememberSaveable { mutableStateOf(false) }

    FormFields(
        listOf(
            FormField(
                value = lentTo,
                onValueChange = onLentToChange,
                placeholder = "Lent to",
                label = "Lent to"
            ),
            FormField(
                value = formatDateFromTimestamp(lentDate),
                onValueChange = { },
                placeholder = "YYYY-MM-DD",
                label = "Lent Date (YYYY-MM-DD)",
                trailingIcon = {
                    IconButton(
                        onClick = { isLentDatePickerVisible = true },
                        icon = Icons.Default.DateRange,
                        iconDescription = "Select Date",
                        tooltip = "Select Date"
                    )
                },
                readOnly = true
            ),
            FormField(
                value = formatDateFromTimestamp(lentReturned),
                onValueChange = { },
                placeholder = "YYYY-MM-DD",
                label = "Return Date (YYYY-MM-DD)",
                trailingIcon = {
                    IconButton(
                        onClick = { isReturnDatePickerVisible = true },
                        icon = Icons.Default.DateRange,
                        iconDescription = "Select Date",
                        tooltip = "Select Date"
                    )
                },
                readOnly = true
            )
        )
    )

    if (isLentDatePickerVisible) {
        DatePickerModal(
            onDateSelected = { onLentDateChange(it ?: 0L) },
            onDismiss = { isLentDatePickerVisible = false }
        )
    }

    if (isReturnDatePickerVisible) {
        DatePickerModal(
            onDateSelected = { onLentReturnedChange(it ?: 0L) },
            onDismiss = { isReturnDatePickerVisible = false }
        )
    }
}