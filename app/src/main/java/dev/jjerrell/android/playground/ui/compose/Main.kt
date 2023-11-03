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
import androidx.navigation.compose.NavHost
import dev.jjerrell.android.playground.base.android.nav.BasePlaygroundNavigation
import dev.jjerrell.android.playground.base.android.nav.BottomNavScreen
import dev.jjerrell.android.playground.base.android.nav.PlaygroundController
import dev.jjerrell.android.playground.base.android.nav.PlaygroundNavGroup
import dev.jjerrell.android.playground.base.android.nav.compose.composable
import dev.jjerrell.android.playground.demo.navigation.demoNavigation
import dev.jjerrell.android.playground.feature.about.navigation.aboutNavigation
import kotlinx.coroutines.delay

/**
 * The main composable controlling the navigation hierarchy
 *
 * @param modifier
 * @param navController
 */
@Composable
fun Main(modifier: Modifier = Modifier, navController: PlaygroundController) {
    /** Capture the back stack entry as a mutable state */
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    // region Navigation action hoisting
    /** Pops the backstack */
    val onBackAction: () -> Unit = { navController.popBackStack() }

    /** Attempts to navigate to a path on the current hierarchy. Logs the event to analytics. */
    val onLocalNavigation: (path: BasePlaygroundNavigation) -> Unit = { path ->
        navController.navigate(path)
    }

    /**
     * Action used when leaving the current navigation hierarchy.
     * - Pops up to the start destination and saves state
     * - Protects against relaunching the same destination
     * - Logs the event to analytics
     */
    val onModularNavigation: (route: BasePlaygroundNavigation) -> Unit = { route ->
        navController.navigate(route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.findStartDestination().id) { saveState = true }
            // Avoid multiple copies of the same destination when
            // relaunching the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    // endregion
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar {
                BottomNavScreen.pages.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceName)) },
                        selected =
                            navBackStackEntry?.destination?.route?.contains(screen.route) == true,
                        onClick = { onModularNavigation(screen) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController.hostController,
            startDestination = LandingStart.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(LandingStart) {
                LaunchedEffect(Unit) {
                    delay(500L)
                    onLocalNavigation(BottomNavScreen.Home)
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

/** The initial [BasePlaygroundNavigation] page for the application; A welcome screen. */
data object LandingStart : PlaygroundNavGroup(navHostRoute = "landing") {
    override val relativePath: String
        get() = "start"
}
