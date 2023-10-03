/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.navigation

interface BasePlaygroundNavigation {
    val path: String
    val deepLinks: List<String>?
}

interface PlaygroundNavigationGroup : BasePlaygroundNavigation {
    val startRoute: BasePlaygroundNavigation
}
