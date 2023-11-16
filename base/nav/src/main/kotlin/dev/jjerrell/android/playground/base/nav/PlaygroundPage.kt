/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.nav

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

interface PlaygroundGroup {
    val hostRoute: String?
        get() = null

    val startRoute: PlaygroundPage
}

interface PlaygroundPage {
    @get:StringRes val titleRes: Int?
    val path: String
    val deeplink: String?
        get() = null

    fun buildPath(tokenMap: Map<String, String> = emptyMap()): String {
        return if (tokenMap.isEmpty()) path
        else
            with(path) {
                var mutableRoute = this
                tokenMap.forEach { (key, value) ->
                    mutableRoute = mutableRoute.replace("{$key}", value)
                }
                mutableRoute
            }
    }
}

// region Bottom Nav Screen
data object BottomNavGroup : PlaygroundGroup {
    override val hostRoute: String
        get() = "main"
    override val startRoute: PlaygroundPage
        get() = Page.Home

    enum class Page(val icon: ImageVector) : PlaygroundPage {
        Home(Icons.Outlined.Home) {
            override val titleRes: Int
                get() = R.string.bottom_nav_demo

            override val path: String
                get() = "demo"
        },
        About(Icons.Outlined.Person) {
            override val titleRes: Int
                get() = R.string.bottom_nav_about

            override val path: String
                get() = "about"
        }
    }
}
// endregion
