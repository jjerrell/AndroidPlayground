/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavGraphBuilder
import dev.jjerrell.android.playground.base.android.nav.BasePlaygroundNavigation
import dev.jjerrell.android.playground.base.android.nav.PlaygroundNavGroup
import dev.jjerrell.android.playground.base.android.nav.PlaygroundNavigationGroup
import dev.jjerrell.android.playground.base.android.nav.compose.composable
import dev.jjerrell.android.playground.base.android.nav.compose.navigation
import dev.jjerrell.android.playground.demo.R
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
    onNavigationAction: (route: BasePlaygroundNavigation) -> Unit
) {
    navigation(DemoNavigationGroup) {
        composable(navRoute = DemoNavigationGroup.Home) {
            DemoListPage(onRequestDemo = { demoNavItem -> onNavigationAction(demoNavItem) })
        }
        composable(navRoute = DemoNavigationGroup.Logging) {
            LoggingPage(onBackAction = onBackAction)
        }
    }
}

/**
 * Local wrapper for [BasePlaygroundNavigation], adding additional properties
 *
 * @constructor Create empty Demo navigation item
 */
internal interface DemoNavigationItem {
    /** The string resource for this pages button and title */
    @get:StringRes val pageTitleRes: Int
}

sealed class DemoNavigationGroup : PlaygroundNavGroup("demo") {
    /** The Demo landing page definition */
    data object Home : DemoNavigationGroup() {
        override val relativePath: String = "home"
    }

    /** The Logging demo page */
    data object Logging : DemoNavigationGroup(), DemoNavigationItem {
        @StringRes override val pageTitleRes: Int = R.string.demo_logging_title
        override val relativePath: String = "logging"
    }
}
