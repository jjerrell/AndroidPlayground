/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.ui.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.jjerrell.android.playground.feature.about.Contributor
import dev.jjerrell.android.playground.feature.about.ContributorRepository

/**
 * View model for [AboutPage]
 *
 * @property repository the injected contributor repository
 */
class AboutViewModel(private val repository: ContributorRepository) : ViewModel() {
    /**
     * State value for Compose observations. Begins as uninitialized (all null properties) and
     * changes to the parameter via `.copy` will trigger recomposition if any values change
     */
    var state by mutableStateOf(AboutState())
        private set

    init {
        getContributors()
    }

    /**
     * Attempts to retrieve a list of [Contributor]s from the [repository] and updates [state] with
     * the result.
     */
    private fun getContributors() {
        state =
            try {
                val contributors = repository.getContributors()
                state.copy(contributors = contributors)
            } catch (e: Throwable) {
                val errors = state.errors.orEmpty().plus(e)
                state.copy(errors = errors)
            }
    }
}

/**
 * [AboutViewModel] state object.
 *
 * @constructor Create uninitialized About state
 * @property contributors the users who have contributed to the app. Default: null
 * @property errors any errors encountered during data retrieval. Default: null
 */
data class AboutState(
    val contributors: List<Contributor>? = null,
    val errors: List<Throwable>? = null
)
