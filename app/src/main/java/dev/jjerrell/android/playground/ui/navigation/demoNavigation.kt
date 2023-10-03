/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.jjerrell.android.playground.base.android.navigation.BasePlaygroundNavigation
import dev.jjerrell.android.playground.base.android.navigation.BottomNavScreen
import dev.jjerrell.android.playground.base.android.navigation.PlaygroundNavigationGroup
import dev.jjerrell.android.playground.ui.compose.logging.basic.LoggingPage

fun NavGraphBuilder.demoNavigation() {
    navigation(startDestination = DemoNavigation.Home.path, route = BottomNavScreen.Demo.route) {
        composable(route = DemoNavigation.Home.path) { Text("Hello") }
        composable(route = DemoNavigation.Logging.path) { LoggingPage() }
    }
}

sealed class DemoNavigation : PlaygroundNavigationGroup {
    override val route: BasePlaygroundNavigation = Home

    data object Home : DemoNavigation() {
        override val path: String = "home"
        override val deepLinks: List<String>?
            get() = null
    }

    data object Logging : DemoNavigation() {
        override val path: String = "logging"
        override val deepLinks: List<String>?
            get() = null
    }
}
