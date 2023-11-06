/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.nav.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.Navigator
import androidx.navigation.compose.rememberNavController
import dev.jjerrell.android.playground.base.nav.PlaygroundController

@Composable
fun rememberPlaygroundController(vararg navigators: Navigator<out NavDestination>) =
    PlaygroundController(hostController = rememberNavController(*navigators))
