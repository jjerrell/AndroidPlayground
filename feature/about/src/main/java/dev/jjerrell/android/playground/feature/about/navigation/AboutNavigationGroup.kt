/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import dev.jjerrell.android.playground.base.android.navigation.BasePlaygroundNavigation
import dev.jjerrell.android.playground.base.android.navigation.PlaygroundNavigationGroup
import dev.jjerrell.android.playground.base.android.navigation.composable
import dev.jjerrell.android.playground.base.android.navigation.navigation
import dev.jjerrell.android.playground.feature.about.ui.compose.AboutPage

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3Api::class
)
fun NavGraphBuilder.aboutNavigation() {
    navigation(AboutNavigationGroup) { composable(AboutNavigationGroup.Home) { AboutPage() } }
}

data object AboutNavigationGroup : PlaygroundNavigationGroup {
    override val route: String = "about"

    override val pages: List<BasePlaygroundNavigation> = listOf(Home)

    data object Home : BasePlaygroundNavigation {
        override val path: String
            get() = "home"

        override val deepLinks: List<String>?
            get() = null
    }
}
