/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.data

import dev.jjerrell.android.playground.feature.about.model.ContributorAttribution
import dev.jjerrell.android.playground.feature.about.model.ContributorInfoKey

internal enum class Attributions(
    val ownerName: String,
    val additionalInfo: Map<String, String?>,
) {
    GOOGLE(
        ownerName = "Google",
        additionalInfo =
            mapOf(
                ContributorInfoKey.USAGE_INFO to "Splash screen and main label",
                ContributorInfoKey.LICENSE to
                    "The Android robot is reproduced or modified from work created and shared by Google and used according to terms described in the Creative Commons 3.0 Attribution License."
            )
    );

    fun toModel(): ContributorAttribution = ContributorAttribution(ownerName, additionalInfo)
}
