/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.model

object ContributorInfoKey {
    const val ICON_URL = "iconUrl"
    const val USAGE_INFO = "usage"
    const val LICENSE = "license"
}

sealed interface Contributor {
    val ownerName: String
    val additionalInfo: Map<String, String?>
}

/**
 * Definition for a contributor to this application
 *
 * @constructor Create new Contributor
 * @property username the github username. Note: Do not prefix with @
 * @property iconUrl the url for the users profile picture
 */
data class ContributorDirect(val username: String, val iconUrl: String?) : Contributor {
    override val ownerName: String
        get() = username

    override val additionalInfo: Map<String, String?>
        get() = mapOf(ContributorInfoKey.ICON_URL to iconUrl)
}

data class ContributorAttribution(
    override val ownerName: String,
    override val additionalInfo: Map<String, String?>
) : Contributor
