/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import dev.jjerrell.android.playground.base.android.R

/**
 * Container for the BottomNavBar items
 *
 * @property route the start route of the navigation hierarchy
 * @property resourceName the title of the page
 * @property icon the [ImageVector] for the page. i.e. [Icons.Filled.Home]
 */
sealed class BottomNavScreen(
    val route: String,
    @StringRes val resourceName: Int,
    val icon: ImageVector
) : BasePlaygroundNavigation {
    override val path: String
        get() = this.route

    override val deepLinks: List<String>?
        get() = null

    /** Home feature navigation hierarchy */
    data object Home : BottomNavScreen("demo", R.string.navigation_demo, icon = Icons.Filled.Home)

    /** Navigation hierarchy for feature-about */
    data object About :
        BottomNavScreen("about", R.string.navigation_about, icon = Icons.Filled.Person)

    companion object {
        /** All of the items in this collection as a list */
        val pages: List<BottomNavScreen> = listOf(Home, About)
    }
}
