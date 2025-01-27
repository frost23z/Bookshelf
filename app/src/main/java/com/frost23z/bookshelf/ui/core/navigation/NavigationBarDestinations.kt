package com.frost23z.bookshelf.ui.core.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationBarDestinations {
	@Serializable
	object Library

	@Serializable
	object Reading

	@Serializable
	object AddEdit

	@Serializable
	object LentBorrowed

	@Serializable
	object More

	companion object {
		val navItems = listOf(
			Library,
			Reading,
			AddEdit,
			LentBorrowed,
			More
		)
	}
}