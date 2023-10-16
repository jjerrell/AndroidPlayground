/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.logging.ui.compose.logging.basic

/** Test/identification tags for the Basic Logging demo */
internal object BasicLoggingTag {
    private const val SUCCESS_BUTTON = "SUCCESSFUL"
    private const val FAILURE_BUTTON = "FAILURE"
    private const val WARNING_BUTTON = "WARNING"

    /** Test tag for the Logging Demo page header */
    const val PageHeader = "LOGGING_PAGE_HEADER"

    /** Creates a tag for each of the log level section headers */
    val LogLevelHeader = { buttonName: String -> "LOG_LEVEL_HEADER_$buttonName" }

    /** Creates a tag for a custom button at a given index */
    val CustomLogLevelButton = { index: Int -> "LOGGING_BUTTON_CUSTOM_$index" }

    /**
     * Builds tags for the log buttons.
     *
     * Example output:
     * ```
     * SUCCESS_LOGGING_BUTTON_1
     * WARNING_LOGGING_BUTTON_1
     * ```
     *
     * Local measures will be needed to guard against duplicates if such a condition arises.
     */
    val LogLevelButton = { logLevel: Int, success: Boolean? ->
        val buttonTagName =
            when (success) {
                true -> SUCCESS_BUTTON
                false -> FAILURE_BUTTON
                else -> WARNING_BUTTON
            }
        "${buttonTagName}_LOGGING_BUTTON_${logLevel}"
    }
}
