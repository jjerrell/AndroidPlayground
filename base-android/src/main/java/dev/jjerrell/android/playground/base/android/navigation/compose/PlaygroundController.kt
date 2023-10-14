/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.navigation.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import dev.jjerrell.android.playground.base.android.navigation.PlaygroundController

/**
 * @see NavHostController
 * @see rememberNavController
 */
@Composable
fun rememberPlaygroundController(vararg navigators: Navigator<out NavDestination>) =
    PlaygroundController(rememberNavController(*navigators))
