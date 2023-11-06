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

abstract class PlaygroundGroup(val hostRoute: String? = null) {
    abstract val startRoute: PlaygroundPage
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

// region NavGraph
/**
 * Wrapper for building NavGraphs using [PlaygroundGroup].
 *
 * @param group the playground navigation group containing routes for this hierarchy
 * @param builder
 * @see androidx.navigation.compose.navigation
 */
inline fun NavGraphBuilder.navigation(
    group: PlaygroundGroup,
    builder: NavGraphBuilder.() -> Unit
): Unit =
    destination(
        NavGraphBuilder(provider, startDestination = group.startRoute.path, route = group.hostRoute)
            .apply(builder)
    )

/**
 * Wrapper for defining a navigation route using [PlaygroundPage].
 *
 * @see composable
 */
fun NavGraphBuilder.composable(
    navRoute: PlaygroundPage,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        null,
    exitTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        null,
    popEnterTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    popExitTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) =
    composable(
        navRoute.path,
        arguments,
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition,
        content
    )
// endregion

// region Bottom Nav Screen
data object BottomNavGroup : PlaygroundGroup() {
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
