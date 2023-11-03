/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.base.android.nav

abstract class PlaygroundNavGroup(val navHostRoute: String? = null) {
    protected abstract val relativePath: String
    open val deepLinks: List<String>? = null

    val route: String
        get() = navHostRoute.let { if (it.isNullOrBlank()) "" else "$it/" } + relativePath
}

/** The base interface for all playground app navigation definitions */
interface BasePlaygroundNavigation {
    /** The path to the page including any arguments */
    val path: String

    /** Deep links accepted for reaching this route */
    val deepLinks: List<String>?
}

/** Mimics a modular navigation hierarchy */
interface PlaygroundNavigationGroup : BasePlaygroundNavigation {
    /** Root path for the module */
    val route: String

    /** [BasePlaygroundNavigation] pages for the module */
    val pages: List<BasePlaygroundNavigation>

    /** Start route for the NavGraph */
    val startRoute: BasePlaygroundNavigation
        get() = pages.first()

    override val path: String
        get() = startRoute.path

    override val deepLinks: List<String>?
        get() = pages.map { it.path }.takeUnless { it.isEmpty() }
}
