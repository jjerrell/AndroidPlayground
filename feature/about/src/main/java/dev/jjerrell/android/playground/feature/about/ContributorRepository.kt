/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about

/**
 * Definition for a contributor to this application
 *
 * @constructor Create new Contributor
 * @property username the github username. Note: Do not prefix with @
 * @property iconUrl the url for the users profile picture
 */
data class Contributor(val username: String, val iconUrl: String?)

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
    override fun getContributors(): List<Contributor> =
        Contributors.values().map { Contributor(username = it.username, iconUrl = it.iconUrl) }
}

/**
 * Strongly typed contributor details
 *
 * @property username the contributors Github username without the '@' prefix
 * @property iconUrl the user for the contributors profile picture
 */
private enum class Contributors(val username: String, val iconUrl: String?) {
    Jay(username = "jjerrell", iconUrl = null)
}
