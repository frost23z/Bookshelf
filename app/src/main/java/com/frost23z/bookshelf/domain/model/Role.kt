package com.frost23z.bookshelf.domain.model

enum class Role(val value: String) {
	AUTHOR("Author"),
	EDITOR("Editor"),
	TRANSLATOR("Translator"),
	ILLUSTRATOR("Illustrator"),
	PUBLISHER("Publisher"),
	OTHER_CONTRIBUTOR("Other Contributor")
}