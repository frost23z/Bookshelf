package com.frost23z.bookshelf.ui.more.settings.userinterface

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.frost23z.bookshelf.ui.core.constants.PaddingMedium
import com.frost23z.bookshelf.ui.core.constants.PaddingSmall
import com.frost23z.bookshelf.ui.theme.Theme
import com.frost23z.bookshelf.ui.theme.resolveColorScheme

@Composable
fun ThemeCard(
    theme: Theme,
    isDark: Boolean,
    isAmoledDark: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = resolveColorScheme(theme, isDark, isAmoledDark)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier =
                modifier
                    .fillMaxWidth()
                    .aspectRatio(9f / 16f)
                    .clip(MaterialTheme.shapes.medium)
                    .border(
                        width = 4.dp,
                        color = if (isSelected) colorScheme.primary else DividerDefaults.color,
                        shape = MaterialTheme.shapes.medium
                    ).background(colorScheme.background)
                    .clickable(onClick = onClick)
        ) {
            // App Bar
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(0.1f)
                        .background(colorScheme.surface)
            )

            // Content preview
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(PaddingMedium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(PaddingSmall)
                ) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(
                                    color = colorScheme.primary,
                                    shape = MaterialTheme.shapes.extraSmall
                                )
                    )
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(
                                        color = colorScheme.secondary,
                                        shape = MaterialTheme.shapes.extraSmall
                                    )
                        )
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(
                                        color = colorScheme.tertiary,
                                        shape = MaterialTheme.shapes.extraSmall
                                    )
                        )
                    }
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(
                                        color = colorScheme.inverseSurface,
                                        shape = MaterialTheme.shapes.extraSmall
                                    )
                        )
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(
                                        color = colorScheme.error,
                                        shape = MaterialTheme.shapes.extraSmall
                                    )
                        )
                    }
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .weight(1f),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Filled.CheckCircle,
                                contentDescription = "Selected",
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }
            }

            // Bottom bar
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .weight(0.1f)
                        .background(colorScheme.surfaceContainer)
            )
        }
        Text(
            text =
                theme.name
                    .lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
        )
    }
}