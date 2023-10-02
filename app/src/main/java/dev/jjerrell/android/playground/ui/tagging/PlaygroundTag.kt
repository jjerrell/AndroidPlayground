/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.ui.tagging

object PlaygroundTag {
    object BasicLogger {

        object TestTag {
            private const val SUCCESS_BUTTON = "SUCCESSFUL"
            private const val FAILURE_BUTTON = "FAILURE"
            private const val WARNING_BUTTON = "WARNING"

            const val PageHeader = "LOGGING_PAGE_HEADER"
            val LogLevelHeader = { buttonName: String -> "LOG_LEVEL_HEADER_$buttonName" }
            val CustomLogLevelButton = { index: Int -> "LOGGING_BUTTON_CUSTOM_$index" }
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
    }
}
