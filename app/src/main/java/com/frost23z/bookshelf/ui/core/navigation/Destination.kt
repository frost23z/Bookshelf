package com.frost23z.bookshelf.ui.core.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
	@Serializable
	data object LibraryGraph : Destination

	@Serializable
	data object Library : Destination

	@Serializable
	data class Detail(val id: Long) : Destination

	@Serializable
	data object ReadingGraph : Destination

	@Serializable
	data object Reading : Destination

	@Serializable
	data object AddEditGraph : Destination

	@Serializable
	data object AddEdit : Destination

	@Serializable
	data object LentGraph : Destination

	@Serializable
	data object Lent : Destination

	@Serializable
	data object MoreGraph : Destination

	@Serializable
	data object More : Destination

	companion object {
		val navBarItems = listOf(
			Library,
			Reading,
			Lent,
			More
		)
	}
}