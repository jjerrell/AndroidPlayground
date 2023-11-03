/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.ui.compose

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dev.jjerrell.android.playground.feature.about.ContributorRepository
import dev.jjerrell.android.playground.feature.about.model.Contributor
import dev.jjerrell.android.playground.feature.about.model.ContributorAttribution
import dev.jjerrell.android.playground.feature.about.model.ContributorDirect

interface IAboutViewModel {
    val state: State<AboutState>
    val directContributors: List<ContributorDirect>?
    val indirectContributors: List<ContributorAttribution>?
}

/**
 * View model for [AboutPage]
 *
 * @property repository the injected contributor repository
 */
class AboutViewModel(private val repository: ContributorRepository) : ViewModel(), IAboutViewModel {
    /**
     * State value for Compose observations. Begins as uninitialized (all null properties) and
     * changes to the parameter via `.copy` will trigger recomposition if any values change
     */
    override var state = mutableStateOf(AboutState())
        private set

    override val directContributors by
        mutableStateOf(state.value.contributors?.filterIsInstance<ContributorDirect>())

    override val indirectContributors by
        mutableStateOf(state.value.contributors?.filterIsInstance<ContributorAttribution>())

    init {
        getContributors()
    }

    /**
     * Attempts to retrieve a list of [Contributor]s from the [repository] and updates [state] with
     * the result.
     */
    private fun getContributors() {
        state.value =
            try {
                val contributors = repository.getContributors()
                state.value.copy(contributors = contributors)
            } catch (e: Throwable) {
                val errors = state.value.errors.orEmpty().plus(e)
                state.value.copy(errors = errors)
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
