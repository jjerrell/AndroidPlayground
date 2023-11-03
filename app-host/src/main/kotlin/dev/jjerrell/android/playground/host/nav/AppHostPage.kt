package dev.jjerrell.android.playground.host.nav

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import dev.jjerrell.android.playground.base.android.nav.PlaygroundNavGroup
import dev.jjerrell.android.playground.base.android.theme.AndroidPlaygroundTheme
import dev.jjerrell.android.playground.host.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class AppHostPage : PlaygroundNavGroup("appHost")

//sealed interface AppHostPage {
//    val relativePath: String
//
//    val route: String
//        get() = "appHost/$relativePath"
//}

data object StartPage : AppHostPage() {
    override val relativePath: String = "start"

    @Composable
    operator fun invoke(startRoute: String, navigateToRoute: (String) -> Unit) {
        LaunchedEffect(key1 = Unit, block = { navigateToRoute(startRoute) })
    }
}

@Composable
private fun AndroidPlaygroundWelcomePage() {
    val appName = stringResource(R.string.app_name)
    val appNameContents = appName.asSequence()
    val currentDisplay: String? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            appNameContents.forEach {
                currentDisplay.orEmpty() + it
                delay(500L)
            }
        }
    }

    Row(
        modifier = Modifier
            .clearAndSetSemantics {
                contentDescription = appName
            }
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            targetState = currentDisplay,
            modifier = Modifier.fillMaxWidth(),
            label = "app_name",
            contentAlignment = Alignment.Center
        ) {
            Text(it.orEmpty())
        }
    }
}

@Preview
@Composable
private fun AndroidPlaygroundWelcomePage_Preview() = AndroidPlaygroundTheme {
    AndroidPlaygroundWelcomePage()
}