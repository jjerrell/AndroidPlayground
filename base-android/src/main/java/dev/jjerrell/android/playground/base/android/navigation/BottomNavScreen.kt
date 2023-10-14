/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import dev.jjerrell.android.playground.base.android.R

sealed class BottomNavScreen(
    val route: String,
    @StringRes val resourceName: Int,
    val icon: ImageVector
) : BasePlaygroundNavigation {
    override val path: String
        get() = this.route

    override val deepLinks: List<String>?
        get() = null

    data object Home : BottomNavScreen("demo", R.string.navigation_demo, icon = Icons.Filled.Home)

    data object About :
        BottomNavScreen("about", R.string.navigation_about, icon = Icons.Filled.Person)

    companion object {
        val pages: List<BottomNavScreen> = listOf(Home, About)
    }
}
