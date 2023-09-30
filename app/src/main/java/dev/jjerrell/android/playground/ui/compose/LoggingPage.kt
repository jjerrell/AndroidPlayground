package dev.jjerrell.android.playground.ui.compose

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.lang.UnsupportedOperationException

private const val PAGE_TAG = "LoggingPage"

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun LoggingPage(
    modifier: Modifier = Modifier,
    onLogEvent: (
        level: Int,
        tag: String,
        msg: String?,
        error: Throwable?
    ) -> Unit,
    onInitialComposition: (tag: String) -> Unit
) {
    val viewModel: LoggingPageViewModel = viewModel()
    // For instances such as reporting screen views, use a LaunchedEffect with a constant as the key.
    // Preferably `Unit` since `true`, `false`, or a `const` can make people get trapped trying to
    // figure out if it serves another purpose
    LaunchedEffect(Unit) {
        onInitialComposition(PAGE_TAG)
    }

    // Still using a launched effect since any executions like this would otherwise occur during each
    // recomposition event. Instead, this will only run once for every time viewModel.state receives
    // a new value
    LaunchedEffect(viewModel.state) {
        if (viewModel.state.exception != null) {
            onLogEvent(
                Log.ERROR,
                PAGE_TAG,
                "Error while getting state",
                viewModel.state.exception
            )
        }
        if (viewModel.state.data != null) {
            onLogEvent(
                Log.INFO,
                PAGE_TAG,
                "State received.",
                viewModel.state.exception
            )
        }
    }

    LazyColumn(modifier = modifier) {
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(
                    modifier = Modifier.testTag("LOGGING_PAGE_HEADER"),
                    text = "Data result: ${viewModel.state.data}"
                )
            }
        }
        itemsIndexed(items = viewModel.buttonType.keys.toList()) { index: Int, buttonName: String ->
            val loggingLevel = viewModel.buttonType[buttonName] ?: Log.INFO
            Column(
                modifier = Modifier
                    .testTag("LOG_LEVEL_HEADER_$buttonName")
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("$buttonName - Priority number: $loggingLevel")
                Divider()
            }
            // Any of these buttons will trigger a minimum of two log events:
            // 1. The result of updating the state via the vm method because of the
            //    `LaunchedEffect(viewModel.state)` block above the UI code
            // 2. The explicit `onLogEvent` call in the Buttons `onClick` block
            // 3. [Third "warn" button only] When a failed call is still handled by
            //    setting a default `data` value
            Button(
                modifier = Modifier.testTag("SUCCESSFUL_LOGGING_BUTTON_$loggingLevel"),
                onClick = {
                    viewModel.getNetworkData()
                    onLogEvent(
                        loggingLevel,
                        PAGE_TAG,
                        "$buttonName Button Clicked!",
                        null // no error here
                    )
                }
            ) {
                Text(text = "$buttonName message")
            }
            Button(
                modifier = Modifier.testTag("FAILING_LOGGING_BUTTON_$loggingLevel"),
                onClick = {
                    viewModel.getNetworkDataWithoutSupport()
                    onLogEvent(
                        loggingLevel,
                        PAGE_TAG,
                        "$buttonName Exception Button Clicked!",
                        null // no error here
                    )
                }
            ) {
                Text(text = "$buttonName exception")
            }
            // Log.WARN is the only level which contains an overload lacking an `msg` parameter
            if (loggingLevel == Log.WARN) {
                Button(
                    modifier = Modifier.testTag("WARNING_EXTRA_LOGGING_BUTTON"),
                    onClick = {
                        viewModel.getNetworkDataWithExplicitLogging()
                        onLogEvent(
                            loggingLevel,
                            PAGE_TAG,
                            "$buttonName Exception Button Clicked!",
                            null // no error here
                        )
                    }
                ) {
                    Text(text = "$buttonName - extra overload ")
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .testTag("LOG_LEVEL_HEADER_CUSTOM")
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Low-level Logging")
                Divider()
            }
            Button(
                modifier = Modifier.testTag("LOGGING_BUTTON_CUSTOM"),
                onClick = {
                    Log.println(
                        Log.DEBUG,
                        PAGE_TAG,
                        "Low level logging button clicked!"
                    )
                }
            ) {
                Text(text = "Low-level logging")
            }
            Button(onClick = {
                /**
                 * Retrieves the stack trace string for custom use.
                 *
                 * Note that [Throwable.stackTraceToString] is also available
                 */
                val stackTraceString = Log.getStackTraceString(
                    UnsupportedOperationException("Example Error")
                )
                // Level is defaulted to Log.INFO
                print("_STDOUT: $stackTraceString")
            }) {
                Text("Stdout logging")
            }
        }
    }
}