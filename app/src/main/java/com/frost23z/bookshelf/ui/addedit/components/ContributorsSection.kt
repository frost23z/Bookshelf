package com.frost23z.bookshelf.ui.addedit.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import com.frost23z.bookshelf.data.Roles
import com.frost23z.bookshelf.ui.addedit.components.core.AddFieldButton
import com.frost23z.bookshelf.ui.addedit.components.core.FormField
import com.frost23z.bookshelf.ui.addedit.components.core.FormFields
import com.frost23z.bookshelf.ui.addedit.components.core.TextFieldDropdownLabel
import com.frost23z.bookshelf.ui.core.components.Icon
import com.frost23z.bookshelf.ui.core.components.IconButton


@Composable
fun ContributorsSection(
    contributors: MutableMap<Int, Contributor>,
    onContributorNameChange: (Int, String) -> Unit,
    onContributorRoleChange: (Int, Roles) -> Unit,
    onAddContributor: () -> Unit,
    onRemoveContributor: (Int) -> Unit
) {
    var index = 0
    FormFields(fields = contributors.map { (id, contributor) ->
        FormField(value = contributor.name,
            onValueChange = { newName -> onContributorNameChange(id, newName) },
            placeholder = "Contributor Name",
            label = null,
            labelCustom = {
                TextFieldDropdownLabel(
                    selectedValue = contributor.role,
                    onValueSelected = { newRole -> onContributorRoleChange(id, newRole) },
                    enumClass = Roles::class
                )
            },
            leadingIcon =
            if (index++ == 0) {
                {
                    Icon(
                        icon = Icons.Outlined.Person,
                        iconDescription = "Contributor"
                    )
                }
            } else null,
            trailingIcon = {
                if (contributors.size > 1) {
                    IconButton(
                        onClick = { onRemoveContributor(id) },
                        icon = Icons.Outlined.Delete,
                        iconDescription = "Remove Contributor",
                        tooltip = "Remove Contributor"
                    )
                }
            })
    }, fieldsVisibility = contributors.keys.map { true }, addMoreField = {
        AddFieldButton(label = "Add More Contributor",
            onClickAdder = onAddContributor,
            leadingIcon = {
                Icon(
                    icon = Icons.Filled.PersonAddAlt,
                    iconDescription = "Add Contributor"
                )
            })
    })
}


data class Contributor(val name: String, val role: Roles)