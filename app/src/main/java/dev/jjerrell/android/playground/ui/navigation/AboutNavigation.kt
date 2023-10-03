/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.ui.navigation

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import dev.jjerrell.android.playground.base.android.navigation.BasePlaygroundNavigation
import dev.jjerrell.android.playground.base.android.navigation.BottomNavScreen
import dev.jjerrell.android.playground.base.android.navigation.PlaygroundNavigationGroup

fun NavGraphBuilder.aboutNavigation() {
    navigation(startDestination = AboutNavigation.Home.path, route = BottomNavScreen.About.route) {
        composable(route = AboutNavigation.Home.path) { Text("About section") }
    }
}

sealed class AboutNavigation() : PlaygroundNavigationGroup {
    override val route: BasePlaygroundNavigation = Home

    data object Home : AboutNavigation() {
        override val path: String
            get() = "home"

        override val deepLinks: List<String>?
            get() = null
    }
}
