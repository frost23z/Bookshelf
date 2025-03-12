package com.frost23z.bookshelf.utility

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SnackbarEventObserver(
	snackbarHostState: SnackbarHostState,
	controller: SnackbarController = SnackbarController
) {
	val scope = rememberCoroutineScope()

	ObserveAsEvents(flow = controller.events) { event ->
		scope.launch {
			snackbarHostState.currentSnackbarData?.dismiss()
			val result =
				snackbarHostState.showSnackbar(
					message = event.message,
					actionLabel = event.actionLabel,
					duration = event.duration,
					withDismissAction = event.dismissAction
				)
			if (result == SnackbarResult.ActionPerformed) {
				event.action?.invoke()
			}
		}
	}
}