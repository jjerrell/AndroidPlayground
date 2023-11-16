/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import dev.jjerrell.android.playground.base.nav.PlaygroundGroup
import dev.jjerrell.android.playground.base.nav.PlaygroundPage
import dev.jjerrell.android.playground.base.nav.compose.composable
import dev.jjerrell.android.playground.base.nav.compose.navigation
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
        composable(AboutNavigationGroup.Home) {
            AboutPage(viewModel = koinViewModel())
        }
    }
}

/**
 * Strongly-typed feature-about navigation definition
 *
 * @constructor Create empty About navigation group
 */
data object AboutNavigationGroup : PlaygroundGroup {
    override val startRoute: PlaygroundPage
        get() = Home

    override val hostRoute: String
        get() = "about"

    /** The landing page for feature-about */
    data object Home : PlaygroundPage {
        override val titleRes: Int? = null
        override val path: String
            get() = "home"
    }
}
