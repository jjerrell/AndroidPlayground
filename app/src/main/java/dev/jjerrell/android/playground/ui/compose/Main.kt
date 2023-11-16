/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.analytics.FirebaseAnalytics
import dev.jjerrell.android.playground.base.nav.BottomNavGroup
import dev.jjerrell.android.playground.base.nav.PlaygroundController
import dev.jjerrell.android.playground.base.nav.PlaygroundGroup
import dev.jjerrell.android.playground.base.nav.PlaygroundPage
import dev.jjerrell.android.playground.demo.navigation.demoNavigation
import dev.jjerrell.android.playground.feature.about.navigation.aboutNavigation
import kotlinx.coroutines.delay

/**
 * The main composable controlling the navigation hierarchy
 *
 * @param modifier
 * @param navController
 */
@OptIn(ExperimentalStdlibApi::class)
@Composable
fun Main(
    modifier: Modifier = Modifier,
    navController: PlaygroundController,
    analyticEvent: (name: String, parameters: Map<String, String?>) -> Unit,
    logEvent: (tag: String?, message: String, throwable: Throwable?) -> Unit
) {
    /** Capture the back stack entry as a mutable state */
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    LaunchedEffect(
        key1 = navBackStackEntry,
        block = {
            logEvent(
                "navigation",
                "Id: ${navBackStackEntry?.id}; Name: ${navBackStackEntry?.destination?.route}\n" +
                    "Start destination: ${navController.findStartDestination().route}\n" +
                    "Hierarchy: ${navBackStackEntry?.destination?.hierarchy?.joinToString { it.route ?: "null" }}",
                null
            )
        }
    )
    // region Navigation action hoisting
    /** Pops the backstack */
    val onBackAction: () -> Unit = { navController.popBackStack() }

    /** Attempts to navigate to a path on the current hierarchy. Logs the event to analytics. */
    val onLocalNavigation: (route: PlaygroundPage) -> Unit = { route ->
        navController.navigate(route)
        logEvent(
            "navigation",
            "route: ${route::class.simpleName};\n" +
                "path: ${route.path};\n" +
                "id: ${navBackStackEntry?.id};\n" +
                "destination route: ${navBackStackEntry?.destination?.route};\n",
            null
        )
        analyticEvent(
            FirebaseAnalytics.Event.SCREEN_VIEW,
            mapOf(
                FirebaseAnalytics.Param.SCREEN_NAME to route.javaClass.simpleName,
                FirebaseAnalytics.Param.SCREEN_CLASS to route.path
            )
        )
    }

    /**
     * Action used when leaving the current navigation hierarchy.
     * - Pops up to the start destination and saves state
     * - Protects against relaunching the same destination
     * - Logs the event to analytics
     */
    val onModularNavigation: (route: PlaygroundGroup, page: PlaygroundPage?) -> Unit =
        { group, target ->
            navController.navigate(group) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack as users select items
                popUpTo(navController.findStartDestination().id) { saveState = true }
                // Avoid multiple copies of the same destination when
                // relaunching the same item
                launchSingleTop = true
                // Restore state when re-selecting a previously selected item
                restoreState = true
            }
            // If given, navigate to the destination within the nav group
            val targetDestination = target ?: group.startRoute
            if (target != null && targetDestination != group.startRoute) {
                navController.navigate(targetDestination)
            }
            // Send the even to analytics
            analyticEvent(
                FirebaseAnalytics.Event.SCREEN_VIEW,
                mapOf(
                    FirebaseAnalytics.Param.SCREEN_NAME to targetDestination.path,
                    FirebaseAnalytics.Param.SCREEN_CLASS to group.hostRoute
                )
            )
        }
    // endregion
    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (
                navBackStackEntry
                    ?.destination
                    ?.hierarchy
                    ?.first()
                    ?.route
                    ?.equals(BottomNavGroup.startRoute::class.simpleName, true) == true
            ) {
                BottomAppBar {
                    BottomNavGroup.Page.entries.forEach { screen ->
                        val screenIsSelected =
                            navBackStackEntry?.destination?.hierarchy?.any {
                                it.route.equals(screen.path, true)
                            } == true
                        NavigationBarItem(
                            selected = screenIsSelected,
                            icon = { Icon(screen.icon, contentDescription = null) },
                            enabled = !screenIsSelected,
                            label = { screen.titleRes?.let { Text(stringResource(it)) } },
                            onClick = { onLocalNavigation(screen) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController.hostController,
            startDestination = "start",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("start") {
                LaunchedEffect(Unit) {
                    delay(500L)
                    onLocalNavigation(BottomNavGroup.Page.Home)
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Welcome", style = MaterialTheme.typography.titleLarge)
                }
            }
            demoNavigation(onBackAction = onBackAction, onNavigationAction = onLocalNavigation)
            aboutNavigation()
        }
    }
}
