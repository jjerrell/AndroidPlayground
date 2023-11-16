/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PlaygroundController(val hostController: NavHostController) {
    private val _currentPlaygroundGroupFlow: MutableSharedFlow<PlaygroundGroup> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val currentPlaygroundGroupFlow: Flow<PlaygroundGroup> =
        _currentPlaygroundGroupFlow.asSharedFlow()

    private val _currentPlaygroundPageFlow: MutableSharedFlow<PlaygroundPage> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val currentPlaygroundPageFlow: Flow<PlaygroundPage> =
        _currentPlaygroundPageFlow.asSharedFlow()

    /** @see NavHostController.currentBackStackEntryAsState */
    @Composable
    fun currentBackStackEntryAsState(): State<NavBackStackEntry?> =
        hostController.currentBackStackEntryAsState()

    @Composable
    fun currentPlaygroundGroupAsState(): State<PlaygroundGroup?> =
        currentPlaygroundGroupFlow.collectAsState(initial = null)

    @Composable
    fun currentPlaygroundPageAsState(): State<PlaygroundPage?> =
        currentPlaygroundPageFlow.collectAsState(initial = null)

    /**
     * Find start destination of the current graph
     *
     * @see androidx.navigation.NavGraph
     */
    fun findStartDestination() = hostController.graph.findStartDestination()

    /** @see NavHostController.popBackStack */
    fun popBackStack() = hostController.popBackStack()

    /** @see NavHostController.navigate */
    fun navigate(page: PlaygroundPage, builder: NavOptionsBuilder.() -> Unit = {}) =
        hostController.navigate(route = page.path, builder = builder).also {
            _currentPlaygroundPageFlow.tryEmit(page)
        }

    fun navigate(
        group: PlaygroundGroup,
        page: PlaygroundPage? = null,
        builder: NavOptionsBuilder.() -> Unit = {}
    ) {
        val targetRoute = page ?: group.startRoute
        val targetIsRoot = targetRoute == group.startRoute

        hostController.navigate(
            route = group.hostRoute ?: targetRoute.path,
            builder = {
                if (findStartDestination() != hostController.graph.last()) {
                    popUpTo(findStartDestination().id) { saveState = true }
                    restoreState = true
                    launchSingleTop = true
                }
                if (targetIsRoot) {
                    builder()
                }
            }
        )
        _currentPlaygroundGroupFlow.tryEmit(group)

        if (!targetIsRoot) {
            navigate(targetRoute)
        }
        _currentPlaygroundPageFlow.tryEmit(targetRoute)
    }
}
