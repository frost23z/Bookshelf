package com.frost23z.bookshelf.ui.more

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.frost23z.bookshelf.ui.theme.ThemeMode
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

class ThemeSettingsScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = koinViewModel<ThemeViewModel>()
        val themeConfig by viewModel.themeProperties.collectAsState()

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Theme Mode", style = MaterialTheme.typography.titleMedium)

            MultiChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                ThemeMode.entries.forEachIndexed { index, mode ->
                    SegmentedButton(
                        checked = themeConfig.themeMode == mode,
                        onCheckedChange = { viewModel.updateThemeMode(mode) },
                        shape =
                            SegmentedButtonDefaults.itemShape(
                                index,
                                ThemeMode.entries.size
                            )
                    ) {
                        Text(
                            text =
                                mode.name
                                    .lowercase()
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                        )
                    }
                }
            }

            // AMOLED Dark Mode Toggle
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "AMOLED Dark Mode",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = themeConfig.isAmoledDark,
                    onCheckedChange = { viewModel.toggleAmoledDarkMode(it) }
                )
            }

            // Theme Selector
            Spacer(modifier = Modifier.height(16.dp))
            Text("Select Theme", style = MaterialTheme.typography.titleMedium)
            val themes = listOf("dynamic", "nord")
            themes.forEach { theme ->
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.updateTheme(theme) }
                            .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = themeConfig.themeName == theme,
                        onClick = { viewModel.updateTheme(theme) }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text =
                            theme
                                .lowercase()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}