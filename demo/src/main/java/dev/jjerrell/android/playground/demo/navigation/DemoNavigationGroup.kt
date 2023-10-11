/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import dev.jjerrell.android.playground.base.android.navigation.BasePlaygroundNavigation
import dev.jjerrell.android.playground.base.android.navigation.PlaygroundNavigationGroup
import dev.jjerrell.android.playground.base.android.navigation.composable
import dev.jjerrell.android.playground.base.android.navigation.navigation
import dev.jjerrell.android.playground.demo.logging.ui.compose.logging.basic.LoggingPage
import dev.jjerrell.android.playground.demo.ui.DemoListPage

fun NavGraphBuilder.demoNavigation(controller: NavController) {
    navigation(DemoNavigationGroup) {
        composable(navRoute = DemoNavigationGroup.Home) {
            DemoListPage(
                onRequestDemo = { demoNavItem -> controller.navigate(route = demoNavItem.path) }
            )
        }
        composable(navRoute = DemoNavigationGroup.Logging) { LoggingPage() }
    }
}

data object DemoNavigationGroup : PlaygroundNavigationGroup {
    override val route: String = "demo"
    override val startRoute: BasePlaygroundNavigation
        get() = Home

    override val pages: List<BasePlaygroundNavigation> = listOf(Logging)

    data object Home : BasePlaygroundNavigation {
        override val path: String = "home"
        override val deepLinks: List<String>?
            get() = null
    }

    data object Logging : BasePlaygroundNavigation {
        override val path: String = "logging"
        override val deepLinks: List<String>?
            get() = null
    }
}
