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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.jjerrell.android.playground.base.android.navigation.BottomNavScreen
import dev.jjerrell.android.playground.demo.navigation.demoNavigation
import dev.jjerrell.android.playground.feature.about.navigation.aboutNavigation
import kotlinx.coroutines.delay

@Composable
fun Main(modifier: Modifier = Modifier, navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination by remember { derivedStateOf { navBackStackEntry?.destination } }
    val onBackAction: () -> Unit = { navController.popBackStack() }
    val onLocalNavigation: (path: String) -> Unit = { path -> navController.navigate(path) }
    val onModularNavigation: (route: String) -> Unit = { route ->
        navController.navigate(route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar {
                BottomNavScreen.pages.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceName)) },
                        selected = currentDestination?.let { it.route == screen.route } == true,
                        onClick = { onModularNavigation(screen.route) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "start",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("start") {
                LaunchedEffect(Unit) {
                    delay(500L)
                    onLocalNavigation(BottomNavScreen.Home.route)
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
