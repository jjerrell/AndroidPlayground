/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.nav.compose

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
import dev.jjerrell.android.playground.base.nav.PlaygroundGroup
import dev.jjerrell.android.playground.base.nav.PlaygroundPage

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
