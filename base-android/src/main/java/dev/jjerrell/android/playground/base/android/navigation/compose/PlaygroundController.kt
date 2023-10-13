/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jjerrell.android.playground.base.android.navigation.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.jjerrell.android.playground.base.android.navigation.PlaygroundController

/**
 * @return a mutable state of the current back stack entry
 */
@Composable
fun PlaygroundController.currentBackStackEntryAsState(): State<NavBackStackEntry?> {
    return (this as NavHostController).currentBackStackEntryAsState()
}

/**
 * @see NavHostController
 * @see rememberNavController
 */
@Composable
fun rememberPlaygroundController(
    vararg navigators: Navigator<out NavDestination>
): PlaygroundController = rememberNavController(*navigators) as PlaygroundController