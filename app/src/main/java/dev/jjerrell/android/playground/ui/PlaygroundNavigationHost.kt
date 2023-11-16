package dev.jjerrell.android.playground.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import dev.jjerrell.android.playground.base.nav.PlaygroundController
import dev.jjerrell.android.playground.base.nav.PlaygroundGroup
import dev.jjerrell.android.playground.base.nav.PlaygroundPage
import dev.jjerrell.android.playground.base.nav.compose.rememberPlaygroundController
import dev.jjerrell.android.playground.demo.navigation.DemoNavigationGroup
import dev.jjerrell.android.playground.feature.about.navigation.AboutNavigationGroup

private class PlaygroundNavigationHostViewModel : ViewModel() {
    var navBarState: PlaygroundNavBar by mutableStateOf(PlaygroundNavBar.Home)
        private set
    var currentPageState: PlaygroundPage by mutableStateOf(PlaygroundNavBar.Home.startRoute)
        private set

    val isTopLevelPage: Boolean by derivedStateOf {
        val currentGroupStart = when (val capturedState = navBarState) {
            is PlaygroundNavBar.Home -> capturedState.startRoute
            is PlaygroundNavBar.About -> capturedState.startRoute
        }
        currentPageState == currentGroupStart
    }
}

@Composable
fun PlaygroundNavigationHost(
    modifier: Modifier = Modifier,
    navController: PlaygroundController = rememberPlaygroundController(),
    analyticEvent: (name: String, parameters: Map<String, String?>) -> Unit,
    logEvent: (tag: String?, message: String, throwable: Throwable?) -> Unit
) {

}

sealed interface PlaygroundNavBar : PlaygroundPage {
    override val path: String
        get() = "main"

    data object Home : PlaygroundNavBar, PlaygroundGroup by DemoNavigationGroup {
        override val titleRes: Int? = startRoute.titleRes
    }
    data object About : PlaygroundNavBar, PlaygroundGroup by AboutNavigationGroup {
        override val titleRes: Int? = startRoute.titleRes
    }
}