package com.frost23z.bookshelf.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.util.fastForEach
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.frost23z.bookshelf.ui.addedit.AddEditTab
import com.frost23z.bookshelf.ui.addedit.AddOptionsBottomsheet
import com.frost23z.bookshelf.ui.library.LibraryTab
import com.frost23z.bookshelf.ui.more.MoreTab

class HomeScreen : Screen {
    private val tabs =
        listOf(
            LibraryTab,
            AddEditTab,
            MoreTab
        )

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val bottomSheetNavigator = LocalBottomSheetNavigator.current

        val screenModel = koinScreenModel<HomeScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        var previousTab by remember { mutableStateOf<Tab?>(null) }
        var currentTab by remember { mutableStateOf<Tab>(LibraryTab) }

        TabNavigator(
            tab = LibraryTab
        ) { tabNavigator ->
            CompositionLocalProvider(LocalNavigator provides navigator) {
                Scaffold(
                    bottomBar = {
                        AnimatedVisibility(
                            visible = state.showBottomNavigation,
                            enter = expandVertically(),
                            exit = shrinkVertically(),
                        ) {
                            NavigationBar {
                                tabs.fastForEach { tab ->
                                    NavigationBarItem(
                                        tab = tab,
                                        selectedTab = currentTab,
                                        onTabSelected = { selectedTab ->
                                            if (selectedTab == currentTab) {
                                                onReselectTab(currentTab, bottomSheetNavigator)
                                            } else {
                                                previousTab = currentTab
                                                currentTab = selectedTab
                                                tabNavigator.current = selectedTab
                                                if (selectedTab == AddEditTab) {
                                                    AddEditTab.previousTab = previousTab
                                                    bottomSheetNavigator.show(AddOptionsBottomsheet())
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    },
                    contentWindowInsets = WindowInsets(0),
                ) { innerPadding ->
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                .consumeWindowInsets(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = tabNavigator.current,
                            transitionSpec = {
                                fadeIn(initialAlpha = 0.5f) togetherWith fadeOut(targetAlpha = 0.5f)
                            },
                            label = "Content"
                        ) {
                            tabNavigator.saveableState(key = "currentTab", it) {
                                it.Content()
                            }
                        }
                    }
                }
            }
            BackHandler(
                enabled = tabNavigator.current != LibraryTab,
                onBack = {
                    tabNavigator.current = LibraryTab
                    currentTab = LibraryTab
                }
            )
        }
    }

    @Composable
    private fun RowScope.NavigationBarItem(
        tab: Tab,
        selectedTab: Tab,
        onTabSelected: (Tab) -> Unit
    ) {
        val selected = selectedTab::class == tab::class
        NavigationBarItem(
            selected = selected,
            onClick = {
                onTabSelected(tab)
            },
            icon = {
                Icon(
                    painter = tab.options.icon!!,
                    contentDescription = tab.options.title,
                    tint = LocalContentColor.current,
                )
            },
            label = {
                Text(
                    text = tab.options.title,
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            alwaysShowLabel = true,
        )
    }

    private fun onReselectTab(
        selectedTab: Tab,
        bottomSheetNavigator: BottomSheetNavigator
    ) {
        when (selectedTab) {
            AddEditTab -> {
                bottomSheetNavigator.show(AddOptionsBottomsheet())
            }
        }
    }
}