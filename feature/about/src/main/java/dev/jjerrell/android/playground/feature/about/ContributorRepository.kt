/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about

data class Contributor(val username: String, val iconUrl: String?)

interface ContributorRepository {
    fun getContributors(): List<Contributor>
}

internal class ContributorRepositoryImpl : ContributorRepository {
    override fun getContributors(): List<Contributor> =
        Contributors.values().map { Contributor(username = it.username, iconUrl = it.iconUrl) }
}

private enum class Contributors(val username: String, val iconUrl: String?) {
    Jay(username = "jjerrell", iconUrl = null)
}
