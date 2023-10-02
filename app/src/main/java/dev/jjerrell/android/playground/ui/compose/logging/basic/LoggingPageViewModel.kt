/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.ui.compose.logging.basic

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.jjerrell.android.playground.ui.tagging.PlaygroundTag
import timber.log.Timber

data class LoggingPageViewModelState(val data: String? = null, val exception: Throwable? = null) {
    val isInitialized = data != null || exception != null
    val isWarning = data != null && exception != null
    val isError = data == null && exception != null
}

@SuppressLint("LogNotTimber")
class LoggingPageViewModel : ViewModel() {

    init {
        Timber.plant(LoggingPageTree())
    }

    val logger: Timber.Tree by lazy {
        Timber.Forest.forest()
            .first { it.isLoggingDemoTree }
            .also { it.v("Demo logger now reporting.") }
    }

    val buttonType: Map<String, Int> =
        mapOf(
            "Verbose" to Log.VERBOSE,
            "Debug" to Log.DEBUG,
            "Info" to Log.INFO,
            "Warn" to Log.WARN,
            "Error" to Log.ERROR,
            "Assert" to Log.ASSERT
        )

    var state by mutableStateOf(LoggingPageViewModelState())
        private set

    init {
        logger.v(message = "View model initialized")
    }

    override fun onCleared() {
        logger.v("Disconnecting standard logger")
        Timber.uproot(LoggingPageTree())
        Timber.v("LoggingPageTree removed: ${Timber.Forest.forest().none { it.isLoggingDemoTree }}")
        super.onCleared()
    }

    /** Simulates the general flow of updating the state, as if from a network connection */
    fun getNetworkData() {
        state = LoggingPageViewModelState(data = methodWhichGets())
    }

    /** Simulates making a request and receiving an exception. */
    fun getNetworkDataWithoutSupport() {
        state =
            try {
                LoggingPageViewModelState(data = methodWhichThrows())
            } catch (e: Throwable) {
                LoggingPageViewModelState(exception = e)
            }
    }

    /**
     * Shares behavior with [getNetworkDataWithoutSupport] but explicitly invokes the logger by
     * issuing a [Log.WARN] message. This one also always sets a "successful" [state] result.
     *
     * Since the compose is observing the updated state, this method will result in an additional
     * `Log.*` message.
     */
    fun getNetworkDataWithExplicitLogging() {
        state =
            try {
                LoggingPageViewModelState(data = methodWhichThrows())
            } catch (e: Throwable) {
                // Use the one-off overload which only is only available to `Log.w`. The only
                // difference with this third overload is that it will not accept a `msg` parameter
                Log.w(PlaygroundTag.BasicLogger.ViewModel, e)
                // Since this is treated as a warning, keep the current value if non-null.
                // Otherwise, set a default state:
                LoggingPageViewModelState(
                    data = state.data ?: "Default feature enabled",
                    exception = e
                )
            }
    }

    /** Method which simply returns a string */
    private fun methodWhichGets(): String = "Enjoy the new feature!"

    /**
     * Method which is expected to return a string. But it always throws instead
     *
     * @return
     */
    private fun methodWhichThrows(): String {
        throw UnsupportedOperationException("Sorry, this action cannot be performed right now.")
    }
}

private val Timber.Tree.isLoggingDemoTree: Boolean
    get() = this is LoggingPageTree
