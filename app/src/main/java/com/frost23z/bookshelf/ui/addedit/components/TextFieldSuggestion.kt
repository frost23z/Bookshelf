package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldSuggestion(
	value: String,
	onValueChange: (String) -> Unit,
	label: String? = null,
	placeholder: String? = label,
	leadingIcon: ImageVector? = null,
	trailingIcon: ImageVector? = null,
	trailingIconClick: () -> Unit = {},
	suggestion: List<String>,
	modifier: Modifier = Modifier,
	contentDescription: String? = null
) {
	var isFocused by rememberSaveable { mutableStateOf(false) }

	val filteredSuggestions = rememberSaveable(suggestion, value) {
		suggestion.filter { it.contains(value, ignoreCase = true) }
	}

	val isMenuExpanded = filteredSuggestions.isNotEmpty() && isFocused

	ExposedDropdownMenuBox(
		expanded = isMenuExpanded,
		onExpandedChange = { },
		modifier = modifier.semantics {
			contentDescription?.let { this.contentDescription = it }
		}
	) {
		TextField(
			value = value,
			onValueChange = {
				onValueChange(it)
				isFocused = true
			},
			label = label,
			placeholder = placeholder,
			leadingIcon = leadingIcon,
			trailingIcon = trailingIcon,
			trailingIconClick = trailingIconClick,
			modifier = Modifier
				.menuAnchor(MenuAnchorType.PrimaryEditable)
				.onFocusEvent { isFocused = it.isFocused }
		)

		ExposedDropdownMenu(
			expanded = isMenuExpanded,
			onDismissRequest = { isFocused = false },
			modifier = Modifier.heightIn(max = 200.dp)
		) {
			filteredSuggestions.forEach { suggestion ->
				DropdownMenuItem(
					text = { Text(suggestion) },
					onClick = {
						onValueChange(suggestion)
						isFocused = false
					}
				)
			}
		}
	}
}