package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ModeEdit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.addedit.components.core.BorderedContainer
import com.frost23z.bookshelf.ui.addedit.components.core.FormField
import com.frost23z.bookshelf.ui.core.components.DateRangePickerModal
import com.frost23z.bookshelf.ui.core.components.IconButton
import com.frost23z.bookshelf.ui.core.constants.LargeIcon
import com.frost23z.bookshelf.ui.core.heplers.formatDateFromTimestamp
import kotlinx.datetime.Clock
import kotlin.math.roundToLong

@Composable
fun StatusSection(
    totalPages: Long,
    readPages: Long,
    onReadPagesChange: (Long) -> Unit,
    onStatusChange: (String) -> Unit,
    startReadingDate: Long,
    onStartReadingDateChange: (Long) -> Unit,
    finishedReadingDate: Long,
    onFinishedReadingDateChange: (Long) -> Unit
) {
    var status by rememberSaveable { mutableStateOf(getInitialStatus(readPages, totalPages)) }
    var showDropdown by rememberSaveable { mutableStateOf(false) }
    var sliderValue by rememberSaveable { mutableFloatStateOf(readPages.toFloat()) }

    val animatedSliderValue by animateFloatAsState(
        targetValue = sliderValue,
        label = "progress"
    )

    val statusOptions =
        mapOf(
            "Unread" to Color(0xFFE57373),
            "Reading" to Color(0xFF81C784),
            "Read" to Color(0xFF4CAF50)
        )

    val currentStatusColor = statusOptions[status] ?: Color.Gray

    val isDatePickerVisible = rememberSaveable { mutableStateOf(false) }

    BorderedContainer(content = {
        DropdownMenu(
            expanded = showDropdown,
            onDismissRequest = { showDropdown = false },
            offset = DpOffset(x = LargeIcon, y = 0.dp),
        ) {
            statusOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.key) },
                    onClick = {
                        handleStatusChange(
                            newStatus = option.key,
                            totalPages = totalPages,
                            onStatusChange = { status = it },
                            onSliderValueChange = { sliderValue = it },
                            onReadPagesChange = onReadPagesChange,
                            onStartDateChange = onStartReadingDateChange,
                            onFinishDateChange = onFinishedReadingDateChange
                        )
                        showDropdown = false
                    }
                )
            }
        }
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

        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = animatedSliderValue.toInt().toString(),
                textAlign = TextAlign.Center,
                modifier = Modifier.width(LargeIcon),
            )

            Slider(
                value = animatedSliderValue,
                onValueChange = {
                    sliderValue = it
                    onReadPagesChange(sliderValue.roundToLong())

                    status =
                        when (sliderValue) {
                            totalPages.toFloat() -> "Read"
                            0f -> "Unread"
                            else -> "Reading"
                        }
                    onStatusChange(status)
                    handleDateUpdates(
                        newStatus = status,
                        onStartDateChange = onStartReadingDateChange,
                        onFinishDateChange = onFinishedReadingDateChange
                    )
                },
                steps = calculateSliderSteps(totalPages),
                valueRange = 0f..totalPages.toFloat(),
                modifier = Modifier.weight(1f),
                colors =
                    SliderDefaults.colors(
                        thumbColor = currentStatusColor,
                        activeTrackColor = currentStatusColor,
                        activeTickColor = currentStatusColor,
                        inactiveTickColor = currentStatusColor.copy(alpha = 0.5f)
                    )
            )

            Text(
                text = totalPages.toString(),
                modifier = Modifier.width(LargeIcon),
                textAlign = TextAlign.Center
            )
        }

        FormField(
            FormField(
                value = formatDateFromTimestamp(startReadingDate),
                onValueChange = { },
                placeholder = "YYYY-MM-DD",
                label = "Start Date (YYYY-MM-DD)",
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
        FormField(
            FormField(
                value = formatDateFromTimestamp(finishedReadingDate),
                onValueChange = { },
                placeholder = "YYYY-MM-DD",
                label = "Finish Date (YYYY-MM-DD)",
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
    })
    if (isDatePickerVisible.value) {
        DateRangePickerModal(
            onDateRangeSelected = {
                onStartReadingDateChange(it.first ?: 0L)
                onFinishedReadingDateChange(it.second ?: 0L)
            },
            onDismiss = { isDatePickerVisible.value = false },
            initialSelectedStartDateMillis = if (startReadingDate == 0L) null else startReadingDate,
            initialSelectedEndDateMillis = if (finishedReadingDate == 0L) null else finishedReadingDate
        )
    }
}

private fun handleStatusChange(
    newStatus: String,
    totalPages: Long,
    onStatusChange: (String) -> Unit,
    onSliderValueChange: (Float) -> Unit,
    onReadPagesChange: (Long) -> Unit,
    onStartDateChange: (Long) -> Unit,
    onFinishDateChange: (Long) -> Unit
) {
    onStatusChange(newStatus)

    val newSliderValue =
        when (newStatus) {
            "Unread" -> 0f
            "Read" -> totalPages.toFloat()
            else -> null
        }

    newSliderValue?.let {
        onSliderValueChange(it)
        onReadPagesChange(it.roundToLong())
    }

    handleDateUpdates(
        newStatus = newStatus,
        onStartDateChange = onStartDateChange,
        onFinishDateChange = onFinishDateChange
    )
}

private fun handleDateUpdates(
    newStatus: String,
    onStartDateChange: (Long) -> Unit,
    onFinishDateChange: (Long) -> Unit
) {
    when (newStatus) {
        "Unread" -> {
            onStartDateChange(0)
            onFinishDateChange(0)
        }
        "Reading" -> onStartDateChange(Clock.System.now().toEpochMilliseconds())
        "Read" -> onFinishDateChange(Clock.System.now().toEpochMilliseconds())
    }
}

private fun getInitialStatus(
    readPages: Long,
    totalPages: Long
): String =
    when {
        readPages >= totalPages -> "Read"
        readPages > 0 -> "Reading"
        else -> "Unread"
    }

private fun calculateSliderSteps(totalPages: Long): Int =
    when (totalPages) {
        in 0..1 -> 0
        in 2..50 -> totalPages.toInt() - 1
        else -> 50
    }