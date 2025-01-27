package com.frost23z.bookshelf.ui.core.components

import androidx.compose.runtime.Composable
import java.util.UUID

interface Screen {
	val key: String get() = "Screen#" + UUID.randomUUID().toString()
	val route: String get() = this::class.qualifiedName ?: key

	@Composable
	fun Content()

	@Composable
	operator fun invoke() = Content()
}