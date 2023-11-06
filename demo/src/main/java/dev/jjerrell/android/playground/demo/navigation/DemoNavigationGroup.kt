/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.navigation

import androidx.navigation.NavGraphBuilder
import dev.jjerrell.android.playground.base.nav.PlaygroundGroup
import dev.jjerrell.android.playground.base.nav.PlaygroundPage
import dev.jjerrell.android.playground.base.nav.composable
import dev.jjerrell.android.playground.base.nav.navigation
import dev.jjerrell.android.playground.demo.logging.ui.compose.logging.basic.LoggingPage
import dev.jjerrell.android.playground.demo.ui.DemoListPage

/**
 * Demo navigation graph builder
 *
 * @param onBackAction the action to take if the user selects a back button
 * @param onNavigationAction the action to take if the user navigates to a different hierarchy
 */
fun NavGraphBuilder.demoNavigation(
    onBackAction: () -> Unit,
    onNavigationAction: (route: PlaygroundPage) -> Unit
) {
    navigation(DemoNavigationGroup) {
        composable(navRoute = DemoNavigationGroup.Page.HOME) {
            DemoListPage(onRequestDemo = { demoNavItem -> onNavigationAction(demoNavItem) })
        }
        composable(navRoute = DemoNavigationGroup.Page.LOGGING) {
            LoggingPage(onBackAction = onBackAction)
        }
    }
}

data object DemoNavigationGroup : PlaygroundGroup("demo") {
    override val startRoute: PlaygroundPage
        get() = Page.HOME

    enum class Page : PlaygroundPage {
        HOME {
            override val titleRes: Int? = null
            override val path: String = "home"
        },
        LOGGING {
            override val titleRes: Int? = null
            override val path: String = "logging"
        }
    }
}
