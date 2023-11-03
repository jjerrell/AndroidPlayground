/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.host

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.jjerrell.android.playground.base.android.nav.PlaygroundController
import dev.jjerrell.android.playground.base.android.nav.compose.rememberPlaygroundController
import dev.jjerrell.android.playground.host.nav.AppHostPage
import dev.jjerrell.android.playground.host.nav.StartPage

@Composable
fun AppHost(
    modifier: Modifier = Modifier,
    controller: PlaygroundController,
    startRoute: String
) {
    NavHost(
        navController = controller.hostController,
        startDestination = StartPage.route,
        modifier = modifier
    ) {
        composable(StartPage.route) {
            StartPage(
                startRoute = startRoute,
                navigateToRoute = {
                    controller.hostController.navigate(it) {
                        popUpTo(StartPage.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

