/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.navigation

/** The base interface for all playground app navigation definitions */
interface BasePlaygroundNavigation {
    /** The path to the page including any arguments */
    val path: String

    /** Deep links accepted for reaching this route */
    val deepLinks: List<String>?
}

/** Mimics a modular navigation hierarchy */
interface PlaygroundNavigationGroup {
    /** Root path for the module */
    val route: String

    /** [BasePlaygroundNavigation] pages for the module */
    val pages: List<BasePlaygroundNavigation>

    /** Start route for the NavGraph */
    val startRoute: BasePlaygroundNavigation
        get() = pages.first()
}
