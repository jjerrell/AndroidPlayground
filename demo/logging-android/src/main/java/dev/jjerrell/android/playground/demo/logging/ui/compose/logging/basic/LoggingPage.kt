/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.logging.ui.compose.logging.basic

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.lang.UnsupportedOperationException
import timber.log.Timber

private const val PAGE_TAG = "LoggingPage"

/**
 * Creates a list buttons separated by the available log levels. Each button performs slightly
 * different actions, showcasing the ability to hande many logging scenarios.
 *
 * @param modifier [Modifier] applied to this composables' top-level composable
 * @param onBackAction
 * @receiver
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LoggingPage(modifier: Modifier = Modifier, onBackAction: () -> Unit) {
    val viewModel: LoggingPageViewModel = viewModel<LoggingPageViewModelImpl>()
    //    val BasicLoggingTag = remember { PlaygroundTag.BasicLogger.TestTag }

    // For instances like reporting screen views, use a LaunchedEffect with a constant as the key.
    LaunchedEffect(Unit) { viewModel.logger.v("Loading page") }

    // Still using a launched effect since any executions like this would otherwise occur during
    // each recomposition event. Instead, this will only run once for every time viewModel.state
    // receives a new value
    LaunchedEffect(viewModel.state) {
        Timber.tag(PAGE_TAG)
        if (viewModel.state.isError) {
            viewModel.logger.e(viewModel.state.exception, "Error while getting state")
        } else if (viewModel.state.isWarning) {
            viewModel.logger.w(viewModel.state.exception, "Data possibly stale.")
        } else if (viewModel.state.isInitialized) {
            viewModel.logger.i("State received. Value = ${viewModel.state.data}")
        } else {
            viewModel.logger.v("No state received")
        }
    }
    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                    text = "Data result: ${viewModel.state.data}"
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackAction) {
                    Image(Icons.Filled.ArrowBack, contentDescription = null)
                }
            }
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = viewModel.buttonTypeLogLevelMap.keys.toList()) { buttonName: String ->
                val loggingLevel = viewModel.buttonTypeLogLevelMap[buttonName] ?: Log.INFO
                Column(
                    modifier =
                        Modifier.testTag(BasicLoggingTag.LogLevelHeader(buttonName))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("$buttonName - Priority number: $loggingLevel")
                    Divider()
                }
                // Any of these buttons will trigger a minimum of two log events:
                //   1. The result of updating the state via the vm method because of the
                //      `LaunchedEffect(viewModel.state)` block above the UI code
                //   2. The explicit `onLogEvent` call in the Buttons `onClick` block
                //   3. "Warn" button only. When a failed call is still handled by setting a default
                //      `data` value
                Button(
                    modifier = Modifier.testTag(BasicLoggingTag.LogLevelButton(loggingLevel, true)),
                    onClick = {
                        viewModel.getNetworkData()
                        viewModel.logger.log(priority = loggingLevel, "$buttonName Button Clicked!")
                    }
                ) {
                    Text(text = "$buttonName message")
                }
                Button(
                    modifier =
                        Modifier.testTag(BasicLoggingTag.LogLevelButton(loggingLevel, false)),
                    onClick = {
                        viewModel.getNetworkDataWithoutSupport()
                        viewModel.logger.log(loggingLevel, "$buttonName Exception Button Clicked!")
                    }
                ) {
                    Text(text = "$buttonName exception")
                }
                // Log.WARN is the only level which contains an overload lacking a `msg` parameter
                if (loggingLevel == Log.WARN) {
                    Button(
                        modifier =
                            Modifier.testTag(BasicLoggingTag.LogLevelButton(loggingLevel, null)),
                        onClick = {
                            viewModel.getNetworkDataWithExplicitLogging()
                            viewModel.logger.log(
                                loggingLevel,
                                "$buttonName Exception Button Clicked!"
                            )
                        }
                    ) {
                        Text(text = "$buttonName - extra overload ")
                    }
                }
            }
            item {
                Column(
                    modifier =
                        Modifier.testTag(BasicLoggingTag.LogLevelHeader("CUSTOM_0"))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Low-level Logging")
                    Divider()
                }
                Button(
                    modifier = Modifier.testTag(BasicLoggingTag.CustomLogLevelButton(1)),
                    onClick = {
                        Log.println(Log.DEBUG, PAGE_TAG, "Low level logging button clicked!")
                    }
                ) {
                    Text(text = "Low-level logging")
                }
                Button(
                    modifier = Modifier.testTag(BasicLoggingTag.CustomLogLevelButton(2)),
                    onClick = {
                        /**
                         * Retrieves the stack trace string for custom use.
                         *
                         * Note that [Throwable.stackTraceToString] is also available
                         */
                        val stackTraceString =
                            Log.getStackTraceString(UnsupportedOperationException("Example Error"))
                        // Level is defaulted to Log.INFO
                        print("STDOUT: $stackTraceString")
                    }
                ) {
                    Text("Stdout logging")
                }
            }
            item {
                Column(
                    modifier =
                        Modifier.testTag(BasicLoggingTag.LogLevelHeader("CUSTOM_1"))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Crash")
                    Divider()
                }
                Button(
                    modifier = Modifier.testTag(BasicLoggingTag.CustomLogLevelButton(3)),
                    onClick = { throw RuntimeException("Test exception from demo-logging-android") }
                ) {
                    Text(text = "Force crash")
                }
            }
        }
    }
}
