/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import dev.jjerrell.android.playground.base.android.nav.BasePlaygroundNavigation
import dev.jjerrell.android.playground.base.android.nav.PlaygroundNavigationGroup
import dev.jjerrell.android.playground.base.android.nav.compose.composable
import dev.jjerrell.android.playground.base.android.nav.compose.navigation
import dev.jjerrell.android.playground.feature.about.ui.compose.AboutPage
import org.koin.androidx.compose.koinViewModel

/** Navigation hierarchy for feature-about */
@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
fun NavGraphBuilder.aboutNavigation() {
    navigation(AboutNavigationGroup) {
        composable(AboutNavigationGroup.Home) { AboutPage(viewModel = koinViewModel()) }
    }
}

/**
 * Strongly-typed feature-about navigation definition
 *
 * @constructor Create empty About navigation group
 */
data object AboutNavigationGroup : PlaygroundNavigationGroup {
    override val route: String = "about"

    override val pages: List<BasePlaygroundNavigation> = listOf(Home)

    /** The landing page for feature-about */
    data object Home : BasePlaygroundNavigation {
        override val path: String
            get() = "home"

        override val deepLinks: List<String>?
            get() = null
    }
}
