/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.navigation.compose.rememberNavController
import dev.jjerrell.android.playground.ui.compose.Main
import dev.jjerrell.android.playground.ui.theme.AndroidPlaygroundTheme

@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            AndroidPlaygroundTheme {
                Main(
                    modifier =
                        Modifier.semantics {
                                // This only needs to be set once in any hierarchy of composables.
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
