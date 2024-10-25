package com.frost23z.bookshelf.ui.addedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.frost23z.bookshelf.ui.core.components.IconTextButtonHorizontal

class AddOptionsBottomsheet : Screen {
    @Composable
    override fun Content() {
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        Column(
            modifier = Modifier.padding(24.dp, 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconTextButtonHorizontal(
                icon = Icons.Default.Edit,
                onClick = {
                    bottomSheetNavigator.replace(AddEditScreen())
                },
                text = "Add a new book",
                iconDescription = "Add a new book",
                modifier = Modifier.fillMaxWidth(),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}