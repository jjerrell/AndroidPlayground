/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.navigation

import androidx.annotation.StringRes
import dev.jjerrell.android.playground.base.android.R

sealed class BottomNavScreen(val route: String, @StringRes val resourceId: Int) {
    data object Demo : BottomNavScreen("demo", R.string.navigation_demo)

    data object About : BottomNavScreen("about", R.string.navigation_about)

    companion object {
        val pages: List<BottomNavScreen> = listOf(Demo, About)
    }
}
