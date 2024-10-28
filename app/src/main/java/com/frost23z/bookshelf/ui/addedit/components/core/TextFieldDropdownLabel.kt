package com.frost23z.bookshelf.ui.addedit.components.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.frost23z.bookshelf.data.Roles
import com.frost23z.bookshelf.ui.core.util.GetTextStyle
import kotlin.reflect.KClass

@Composable
fun <T : Enum<T>> TextFieldDropdownLabel(
    selectedValue: T?,
    onValueSelected: (T) -> Unit,
    enumClass: KClass<T>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val defaultValue = enumClass.java.enumConstants?.firstOrNull()
    val currentValue = selectedValue ?: defaultValue

    Column(modifier = modifier) {
        Text(
            text = (currentValue as? Roles)?.value ?: currentValue?.name ?: "",
            modifier = Modifier
                .clickable { expanded = true },
            color = MaterialTheme.colorScheme.primary,
            style = GetTextStyle.labelStyle

        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            enumClass.java.enumConstants?.forEach { value ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = (value as? Roles)?.value ?: value.name
                        )
                    }, // Use displayName if available
                    onClick = {
                        onValueSelected(value)
                        expanded = false
                    }
                )
            }
        }
    }

    if (selectedValue == null && defaultValue != null) {
        onValueSelected(defaultValue)
    }
}

@Preview
@Composable
fun PreviewDropdownLabel() {
    var selectedRole by remember { mutableStateOf<Roles?>(null) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TextFieldDropdownLabel(
            selectedValue = selectedRole,
            onValueSelected = { selectedRole = it },
            enumClass = Roles::class
        )
    }
}