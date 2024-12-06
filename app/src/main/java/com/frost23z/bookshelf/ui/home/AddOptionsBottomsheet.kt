package com.frost23z.bookshelf.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.core.components.IconTextButtonHorizontal
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOptionsBottomSheet(
    onDismissRequest: () -> Unit,
    onAddBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            scope.launch {
                try {
                    onDismissRequest()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        },
        dragHandle = null
    ) {
        Column(
            modifier =
                modifier
                    .padding(24.dp, 16.dp)
                    .navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconTextButtonHorizontal(
                icon = Icons.Default.Edit,
                onClick = onAddBookClick,
                text = "Add a new book",
                iconDescription = "Add a new book",
                modifier = Modifier.fillMaxWidth(),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}