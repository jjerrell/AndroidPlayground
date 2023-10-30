/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.data

import dev.jjerrell.android.playground.feature.about.model.ContributorDirect

/**
 * Strongly typed contributor details
 *
 * @property username the contributors Github username without the '@' prefix
 * @property iconUrl the user for the contributors profile picture
 */
internal enum class Contributors(val username: String, val iconUrl: String?) {
    Jay(username = "jjerrell", iconUrl = null);

    fun toModel(): ContributorDirect = ContributorDirect(username = username, iconUrl = iconUrl)
}
