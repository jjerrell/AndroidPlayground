/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.navigation

import android.os.Bundle
import androidx.annotation.Size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.ParametersBuilder
import com.google.firebase.analytics.ktx.logEvent

/**
 * Playground controller
 *
 * @property hostController
 */
class PlaygroundController(
    val hostController: NavHostController,
    private val analytics: FirebaseAnalytics
) {
    fun logEvent(name: String, block: ParametersBuilder.() -> Unit) {
        analytics.logEvent(name, block)
    }

    fun logEvent(@Size(min = 1L, max = 40L) name: String, params: Bundle?) =
        analytics.logEvent(name, params)

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
    fun navigate(page: BasePlaygroundNavigation, builder: NavOptionsBuilder.() -> Unit = {}) =
        navigate(path = page.path, builder = builder)

    private fun navigate(path: String, builder: NavOptionsBuilder.() -> Unit) =
        hostController.navigate(route = path, builder = builder)
}
