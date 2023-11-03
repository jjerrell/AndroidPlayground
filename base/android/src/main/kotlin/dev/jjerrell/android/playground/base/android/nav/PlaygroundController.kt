/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Playground controller
 *
 * @property hostController
 */
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
    fun navigate(page: PlaygroundNavGroup, builder: NavOptionsBuilder.() -> Unit = {}) =
        navigate(path = page.route, builder = builder)

    private fun navigate(path: String, builder: NavOptionsBuilder.() -> Unit) =
        hostController.navigate(route = path, builder = builder)
}

/** @return the caller of the method which called this method. */
private fun getRecentSteps(): String {
    var recentSteps = ""
    val traceElements = Thread.currentThread().stackTrace
    val maxStepCount = 3
    val skipCount = 2
    for (i in Math.min(maxStepCount + skipCount, traceElements.size) - 1 downTo skipCount) {
        val className =
            traceElements[i].className.substring(traceElements[i].className.lastIndexOf(".") + 1)
        recentSteps += " >> " + className + "." + traceElements[i].methodName + "()"
    }
    return recentSteps
}
