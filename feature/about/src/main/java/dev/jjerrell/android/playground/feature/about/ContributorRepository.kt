/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about

import dev.jjerrell.android.playground.feature.about.data.Attributions
import dev.jjerrell.android.playground.feature.about.data.Contributors
import dev.jjerrell.android.playground.feature.about.model.Contributor
import dev.jjerrell.android.playground.feature.about.model.ContributorAttribution

/** Repository for working with application contributors */
interface ContributorRepository {
    /**
     * Get the list of contributors
     *
     * @return List of [Contributor]s
     */
    fun getContributors(): List<Contributor>
}

/**
 * Contributor repository implementation
 *
 * @constructor Create new [ContributorRepository]
 */
internal class ContributorRepositoryImpl : ContributorRepository {
    override fun getContributors(): List<Contributor> {
        val direct = Contributors.values().map { it.toModel() }
        val thirdParty =
            Attributions.values().map {
                ContributorAttribution(ownerName = it.ownerName, additionalInfo = it.additionalInfo)
            }

        return direct + thirdParty
    }
}
