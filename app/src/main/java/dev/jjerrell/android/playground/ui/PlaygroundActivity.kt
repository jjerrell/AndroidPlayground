/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import dev.jjerrell.android.playground.base.android.navigation.PlaygroundController
import dev.jjerrell.android.playground.base.android.navigation.compose.rememberPlaygroundController
import dev.jjerrell.android.playground.base.android.theme.AndroidPlaygroundTheme
import dev.jjerrell.android.playground.ui.compose.Main
import org.koin.android.ext.android.getKoin
import org.koin.compose.KoinContext

/**
 * Playground activity serves as the main entry point for the application user interface.
 *
 * Configures:
 * - Compose Koin context
 * - The [PlaygroundController]
 * - The compose application theme
 * - The [Main] composable
 * - Sets [SemanticsPropertyReceiver.testTagsAsResourceId] for automation purposes
 */
@OptIn(ExperimentalComposeUiApi::class)
class PlaygroundActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinContext(application.getKoin()) {
                val navHostController: PlaygroundController = rememberPlaygroundController()
                AndroidPlaygroundTheme {
                    Main(
                        modifier =
                            Modifier.semantics {
                                    // This only needs to be set once in any hierarchy of
                                    // composables.
                                    // The descendants will automatically follow this behavior. This
                                    // will simplify automation testing, if explored.
                                    testTagsAsResourceId = true
                                }
                                // The tag identifying this compose component for instrumented or
                                // automated testing.
                                .testTag("TOP_LEVEL_COMPOSABLE"),
                        navController = navHostController
                    )
                }
            }
        }
    }
}