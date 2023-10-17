/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.navigation

import androidx.annotation.StringRes
import androidx.navigation.NavGraphBuilder
import dev.jjerrell.android.playground.base.android.navigation.BasePlaygroundNavigation
import dev.jjerrell.android.playground.base.android.navigation.PlaygroundNavigationGroup
import dev.jjerrell.android.playground.base.android.navigation.compose.composable
import dev.jjerrell.android.playground.base.android.navigation.compose.navigation
import dev.jjerrell.android.playground.demo.logging.ui.compose.logging.basic.LoggingPage
import dev.jjerrell.android.playground.demo.ui.DemoListPage
import dev.jjerrell.android.playground.logging.android.R

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
internal interface DemoNavigationItem : BasePlaygroundNavigation {
    /** The string resource for this pages button and title */
    @get:StringRes val pageTitleRes: Int
}

data object DemoNavigationGroup : PlaygroundNavigationGroup {
    override val route: String = "demo"
    override val startRoute: BasePlaygroundNavigation
        get() = Home

    override val pages: List<BasePlaygroundNavigation> = listOf(Logging)

    /** The Demo landing page definition */
    data object Home : BasePlaygroundNavigation {
        override val path: String = "home"
        override val deepLinks: List<String>?
            get() = null
    }

    /** The Logging demo page */
    data object Logging : DemoNavigationItem {
        @StringRes override val pageTitleRes: Int = R.string.demo_logging_title
        override val path: String = "logging"

        override val deepLinks: List<String>?
            get() = null
    }
}
