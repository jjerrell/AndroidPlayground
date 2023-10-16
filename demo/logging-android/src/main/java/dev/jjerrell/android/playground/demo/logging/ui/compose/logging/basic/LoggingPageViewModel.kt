/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.logging.ui.compose.logging.basic

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import timber.log.Timber

/**
 * Logging page view model state object.
 *
 * @constructor Create empty Logging page view model state
 * @property data the successful response to show in the top app bar, if any response is available
 * @property exception any error caught in the most recent operation
 */
data class LoggingPageViewModelState(val data: String? = null, val exception: Throwable? = null) {
    val isInitialized = data != null || exception != null
    val isWarning = data != null && exception != null
    val isError = data == null && exception != null
}

/**
 * View model definition for the [LoggingPage]
 *
 * @constructor Create empty Logging page view model
 */
interface LoggingPageViewModel {
    val logger: Timber.Tree
    val buttonTypeLogLevelMap: Map<String, Int>
    val state: LoggingPageViewModelState

    /** Simulates the general flow of updating the state, as if from a network connection */
    fun getNetworkData()

    /** Simulates making a request and receiving an exception. */
    fun getNetworkDataWithoutSupport()

    /**
     * Shares behavior with [getNetworkDataWithoutSupport] but explicitly invokes the logger by
     * issuing a [Log.WARN] message. This one also always sets a "successful" [state] result.
     *
     * Since the compose is observing the updated state, this method will result in an additional
     * `Log.*` message.
     */
    fun getNetworkDataWithExplicitLogging()
}

/**
 * Logging page view model implementation
 *
 * @constructor Create new Logging page view model
 */
internal class LoggingPageViewModelImpl : ViewModel(), LoggingPageViewModel {
    init {
        Timber.plant(LoggingPageTree())
    }

    override val logger: Timber.Tree by lazy {
        Timber.Forest.forest()
            .first { it.isLoggingDemoTree }
            .also { it.v("Demo logger now reporting.") }
    }

    override val buttonTypeLogLevelMap: Map<String, Int> =
        mapOf(
            "Verbose" to Log.VERBOSE,
            "Debug" to Log.DEBUG,
            "Info" to Log.INFO,
            "Warn" to Log.WARN,
            "Error" to Log.ERROR,
            "Assert" to Log.ASSERT
        )

    override var state by mutableStateOf(LoggingPageViewModelState())
        private set

    init {
        logger.v(message = "View model initialized")
    }

    override fun onCleared() {
        logger.v("Disconnecting standard logger")
        Timber.uproot(logger)
        Timber.v("LoggingPageTree removed: ${Timber.Forest.forest().none { it.isLoggingDemoTree }}")
        super.onCleared()
    }

    override fun getNetworkData() {
        state = LoggingPageViewModelState(data = methodWhichGets())
    }

    override fun getNetworkDataWithoutSupport() {
        state =
            try {
                LoggingPageViewModelState(data = methodWhichThrows())
            } catch (e: Throwable) {
                LoggingPageViewModelState(exception = e)
            }
    }

    override fun getNetworkDataWithExplicitLogging() {
        state =
            try {
                LoggingPageViewModelState(data = methodWhichThrows())
            } catch (e: Throwable) {
                // Use the one-off overload which only is only available to `Log.w`. The only
                // difference with this third overload is that it will not accept a `msg` parameter
                logger.w(e)
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
