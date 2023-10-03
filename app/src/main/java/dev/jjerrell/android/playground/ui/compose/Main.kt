/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.jjerrell.android.playground.base.android.navigation.BasePlaygroundNavigation
import dev.jjerrell.android.playground.base.android.navigation.BottomNavScreen
import dev.jjerrell.android.playground.ui.navigation.aboutNavigation
import dev.jjerrell.android.playground.ui.navigation.demoNavigation

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun Main(modifier: Modifier = Modifier, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    /**
     * Attempts to resolve the current destination as a [BottomNavScreen]. Value is null if the view
     * is not a member of this type
     */
    val currentNavScreen: BottomNavScreen? =
        currentDestination?.route?.let { route ->
            BottomNavScreen.pages.firstOrNull { it.route == route }
        }

    /**
     * Written as a var so it can be overridden as needed.
     *
     * TODO: Reset to default after leaving an overriding screen
     */
    Scaffold(
        modifier = modifier,
        topBar = {
            if (BottomNavScreen.pages.none { it.route == currentDestination?.route }) {
                TopAppBar(
                    title = {
                        /**
                         * TODO: Resolve destination as a [BasePlaygroundNavigation] and set the
                         *   title.. after adding a title to `BasePlaygroundNavigation`
                         */
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = null)
                        }
                    }
                )
            }
        },
        bottomBar = {
            BottomAppBar {
                BottomNavScreen.pages.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(Icons.Filled.Build, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected =
                            currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
//                                val hierarchyStart = navController.graph.findStartDestination()
//                                if (currentDestination?.route != hierarchyStart.route) {
//                                    popUpTo(navController.graph.findStartDestination().id) {
//                                        saveState = true
//                                    }
//                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
//                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
//                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavScreen.Demo.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            demoNavigation()
            aboutNavigation()
        }
    }
}
