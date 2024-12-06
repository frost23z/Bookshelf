package com.frost23z.bookshelf.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.frost23z.bookshelf.R
import com.frost23z.bookshelf.ui.addedit.AddEditScreen
import com.frost23z.bookshelf.ui.lent.LentTab
import com.frost23z.bookshelf.ui.library.LibraryTab
import com.frost23z.bookshelf.ui.more.MoreTab
import com.frost23z.bookshelf.ui.reading.ReadingTab
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

object HomeScreen : Screen {
    private fun readResolve(): Any = HomeScreen

    private val showBottomNavigationEvent = Channel<Boolean>()

    private val tabs =
        listOf(
            LibraryTab,
            ReadingTab,
            LentTab,
            MoreTab
        )

    @OptIn(ExperimentalAnimationGraphicsApi::class)
    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<HomeScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()

        val navigator = LocalNavigator.currentOrThrow

        val bottomNavigationVisible by produceState(initialValue = true) {
            showBottomNavigationEvent.receiveAsFlow().collectLatest { value = it }
        }

        TabNavigator(
            tab = state.currentTab,
            key = "home_navigator"
        ) { tabNavigator ->
            CompositionLocalProvider(LocalNavigator provides navigator) {
                Scaffold(
                    bottomBar = {
                        AnimatedVisibility(
                            visible = bottomNavigationVisible,
                            enter = expandVertically(),
                            exit = shrinkVertically(),
                        ) {
                            NavigationBar(
                                containerColor = Color.Transparent,
                            ) {
                                Box(modifier = Modifier.fillMaxWidth().height(80.dp)) {
                                    val surfaceContainerColor = MaterialTheme.colorScheme.surfaceContainer
                                    Canvas(modifier = Modifier.fillMaxSize()) {
                                        val cutoutRadius = 40.dp.toPx()
                                        val cutoutPath =
                                            Path().apply {
                                                addOval(Rect(center = Offset(size.width / 2, 0f), radius = cutoutRadius))
                                            }
                                        clipPath(path = cutoutPath, clipOp = ClipOp.Difference) {
                                            drawRect(color = surfaceContainerColor)
                                        }
                                    }
                                    Row(
                                        modifier =
                                            Modifier
                                                .fillMaxWidth()
                                                .align(Alignment.BottomCenter),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        tabs.forEachIndexed { index, tab ->
                                            NavigationBarItem(tab = tab)
                                            if (index == (tabs.size / 2) - 1) {
                                                // Spacer(modifier = Modifier.weight(1f))
                                                NavigationBarMiddleItem(modifier = Modifier.weight(1f))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    },
                    floatingActionButton = {
                        AnimatedVisibility(
                            visible = bottomNavigationVisible,
                            enter = expandHorizontally(),
                            exit = shrinkVertically(),
                        ) {
                            FloatingActionButton(
                                onClick = { screenModel.toggleAddOptionsBottomsheet() },
                                containerColor = MaterialTheme.colorScheme.primary,
                                shape = CircleShape,
                                modifier = Modifier.offset(y = 44.dp),
                            ) {
                                Icon(
                                    rememberAnimatedVectorPainter(
                                        animatedImageVector =
                                            AnimatedImageVector.animatedVectorResource(R.drawable.anim_add),
                                        atEnd = state.showAddOptionsBottomsheet
                                    ),
                                    contentDescription = "Add"
                                )
                            }
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
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
                                if (state.showAddOptionsBottomsheet) {
                                    AddOptionsBottomSheet(
                                        onDismissRequest = {
                                            screenModel.toggleAddOptionsBottomsheet()
                                        },
                                        onAddBookClick = {
                                            screenModel.toggleAddOptionsBottomsheet()
                                            navigator.push(AddEditScreen())
                                        },
                                    )
                                }
                            }
                        }
                    }
                }
            }
            BackHandler(
                enabled = state.showAddOptionsBottomsheet || tabNavigator.current != LibraryTab,
                onBack = {
                    when {
                        state.showAddOptionsBottomsheet -> screenModel.toggleAddOptionsBottomsheet()
                        else -> {
                            tabNavigator.current = LibraryTab
                            screenModel.setCurrentTab(LibraryTab)
                        }
                    }
                }
            )
        }
    }

    @Composable
    private fun RowScope.NavigationBarItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current
        val selected = tabNavigator.current::class == tab::class
        val screenModel = koinScreenModel<HomeScreenModel>()
        val state by screenModel.state.collectAsStateWithLifecycle()
        NavigationBarItem(
            selected = selected && !state.showAddOptionsBottomsheet,
            onClick = {
                if (!selected) {
                    screenModel.setCurrentTab(tab)
                    tabNavigator.current = tab
                }
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
                    overflow = TextOverflow.Ellipsis
                )
            },
            alwaysShowLabel = true,
        )
    }

    @Composable
    private fun NavigationBarMiddleItem(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier.size(80.dp), // Standard NavigationBar Container height
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.padding(top = 32.dp), // 12dp + 16dp + 4dp = 32dp from top
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add",
                    style = MaterialTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

    suspend fun showBottomNavigation(show: Boolean) {
        showBottomNavigationEvent.send(show)
    }
}