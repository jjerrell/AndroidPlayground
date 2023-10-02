/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import dev.jjerrell.android.playground.ui.compose.logging.basic.LoggingPage
import dev.jjerrell.android.playground.ui.theme.AndroidPlaygroundTheme

@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier =
                        Modifier.semantics {
                                // This only needs to be set once in any hierarchy of composables.
                                // The descendants will automatically follow this behavior. This
                                // will simplify automation testing, if explored.
                                testTagsAsResourceId = true
                            }
                            // The tag identifying this compose component for instrumented or
                            // automated testing.
                            .testTag("APPLICATION_SERVICE")
                            .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoggingPage(modifier = Modifier.testTag("LOGGING_PAGE").fillMaxSize())
                }
            }
        }
    }
}
