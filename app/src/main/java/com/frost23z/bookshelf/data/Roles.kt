package com.frost23z.bookshelf.data

enum class Roles(
    val value: String
) {
    AUTHOR("Author"),
    EDITOR("Editor"),
    TRANSLATOR("Translator"),
    ILLUSTRATOR("Illustrator"),
    PUBLISHER("Publisher"),
    OTHER_CONTRIBUTOR("Other Contributor");

    companion object {
        fun fromValue(value: String): Roles? = entries.find { it.value == value }
    }
}