package dev.jjerrell.android.playground

import android.os.Bundle
import android.util.Log
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
import dev.jjerrell.android.playground.ui.compose.LoggingPage
import dev.jjerrell.android.playground.ui.theme.AndroidPlaygroundTheme

const val ACTIVITY_TAG = "MAIN_ACTIVITY"

@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Each `Log.*` call returns the number of bytes written
         */
        val logEventHandler: (
            level: Int,
            tag: String,
            msg: String?,
            error: Throwable?
        ) -> Int = { level: Int, tag: String, msg: String?, error: Throwable? ->
            when (level) {
                Log.VERBOSE -> Log.v(tag, msg, error)
                Log.DEBUG -> Log.d(tag, msg, error)
                Log.INFO -> Log.i(tag, msg, error)
                Log.WARN -> Log.w(tag, msg, error)
                Log.ERROR -> Log.e(tag, msg, error)
                else -> Log.wtf(tag, msg, error)
            }
        }
        setContent {
            AndroidPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .semantics {
                            // This only needs to be set once in any hierarchy of composables. The
                            // descendants will automatically follow this behavior. This will simplify
                            // automation testing, if explored
                            testTagsAsResourceId = true
                        }
                        // The tag identifying this compose component
                        .testTag("APPLICATION_SERVICE")
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoggingPage(
                        modifier = Modifier
                            .testTag("LOGGING_PAGE")
                            .fillMaxSize(),
                        onLogEvent = { level: Int, tag: String, msg: String?, error: Throwable? ->
                            val loggedBytes = logEventHandler(level, tag, msg, error)
                            Log.v(ACTIVITY_TAG, "Wrote bytes: $loggedBytes")
                        },
                        onInitialComposition = { pageTag: String ->
                            Log.v(ACTIVITY_TAG, "Loaded page with tag: $pageTag")
                        }
                    )
                }
            }
        }
    }
}
