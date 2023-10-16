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

internal interface DemoNavigationItem : BasePlaygroundNavigation {
    @get:StringRes val pageTitleRes: Int
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

    data object Logging : DemoNavigationItem {
        @StringRes override val pageTitleRes: Int = R.string.demo_logging_title
        override val path: String = "logging"

        override val deepLinks: List<String>?
            get() = null
    }
}
