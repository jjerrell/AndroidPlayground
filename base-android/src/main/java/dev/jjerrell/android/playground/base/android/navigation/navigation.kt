/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

interface BasePlaygroundNavigation {
    val path: String
    val deepLinks: List<String>?
}

interface PlaygroundNavigationGroup {
    val route: String
    val pages: List<BasePlaygroundNavigation>

    val startRoute: BasePlaygroundNavigation
        get() = pages.first()
}

inline fun NavGraphBuilder.navigation(
    group: PlaygroundNavigationGroup,
    builder: NavGraphBuilder.() -> Unit
): Unit =
    destination(
        NavGraphBuilder(provider, startDestination = group.startRoute.path, route = group.route)
            .apply(builder)
    )

/** @see [composable] */
fun NavGraphBuilder.composable(
    navRoute: BasePlaygroundNavigation,
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
