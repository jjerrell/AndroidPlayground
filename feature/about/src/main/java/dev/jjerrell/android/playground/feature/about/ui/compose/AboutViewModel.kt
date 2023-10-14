/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.ui.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dev.jjerrell.android.playground.feature.about.Contributor
import dev.jjerrell.android.playground.feature.about.ContributorRepository

class AboutViewModel(private val repository: ContributorRepository) : ViewModel() {
    var state by mutableStateOf(AboutState())
        private set

    init {
        getContributors()
    }

    fun getContributors() {
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

data class AboutState(
    val contributors: List<Contributor>? = null,
    val errors: List<Throwable>? = null
)
