/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState

class PlaygroundController(val hostController: NavHostController) {
    /** @see NavHostController.currentBackStackEntryAsState */
    @Composable
    fun currentBackStackEntryAsState(): State<NavBackStackEntry?> =
        hostController.currentBackStackEntryAsState()

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
        hostController.navigate(route = page.path, builder = builder)

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
        if (!targetIsRoot) {
            navigate(targetRoute)
        }
    }
}

inline fun <reified T> PlaygroundController.navigate(
    page: T,
    noinline builder: NavOptionsBuilder.() -> Unit = {}
) where T : PlaygroundPage {
    navigate(page, builder)
}

inline fun <reified T> PlaygroundController.navigate(
    group: T,
    noinline builder: NavOptionsBuilder.() -> Unit = {}
) where T : PlaygroundGroup {
    navigate(group, builder = builder)
}
