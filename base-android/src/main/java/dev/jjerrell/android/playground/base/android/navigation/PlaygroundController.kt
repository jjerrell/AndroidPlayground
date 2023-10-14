/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState

class PlaygroundController(val hostController: NavHostController) {
    @Composable
    fun currentBackStackEntryAsState(): State<NavBackStackEntry?> =
        hostController.currentBackStackEntryAsState()

    fun findStartDestination() = hostController.graph.findStartDestination()

    fun popBackStack() = hostController.popBackStack()

    fun navigate(page: BasePlaygroundNavigation, builder: NavOptionsBuilder.() -> Unit = {}) =
        navigate(path = page.path, builder = builder)

    private fun navigate(path: String, builder: NavOptionsBuilder.() -> Unit) =
        hostController.navigate(route = path, builder = builder)
}
