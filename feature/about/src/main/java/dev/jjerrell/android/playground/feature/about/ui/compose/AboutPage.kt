/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.ui.compose

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jjerrell.android.playground.base.android.theme.AndroidPlaygroundTheme
import dev.jjerrell.android.playground.feature.about.data.Attributions
import dev.jjerrell.android.playground.feature.about.data.Contributors
import dev.jjerrell.android.playground.feature.about.model.ContributorAttribution
import dev.jjerrell.android.playground.feature.about.model.ContributorDirect
import dev.jjerrell.android.playground.feature.about.model.ContributorInfoKey

/**
 * Main composable for feature-about with an injected view model slot
 *
 * @param viewModel the injected [AboutViewModel]
 */
@Composable
@ExperimentalFoundationApi
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
internal fun AboutPage(viewModel: IAboutViewModel) {
    AboutPageLayout(
        modifier = Modifier.fillMaxSize(),
        directContributors = viewModel.directContributors,
        indirectContributors = viewModel.indirectContributors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutPageLayout(
    modifier: Modifier = Modifier,
    directContributors: List<ContributorDirect>?,
    indirectContributors: List<ContributorAttribution>?
) {
    Surface(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.padding(8.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            aboutHeaderItems()
            contributorFlowLayout(
                directContributors = directContributors,
                indirectContributors = indirectContributors
            )
        }
    }
}

private fun LazyListScope.aboutHeaderItems() {
    item {
        Text(
            "A simple playground to experiment, implement, and demonstrate various techniques, architectures, and libraries.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
    item {
        Text(
            "The application and this page specifically is a demonstration of a simple dependency injection configuration using Koin!",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.contributorFlowLayout(
    directContributors: List<ContributorDirect>?,
    indirectContributors: List<ContributorAttribution>?
) {
    if (directContributors?.isNotEmpty() == true) {
        stickyHeader {
            Text("Contributors", style = MaterialTheme.typography.titleMedium)
            Divider()
        }
        directContributors.let { item { DirectContributorFlowRow(directContributors = it) } }
    }

    if (indirectContributors?.isNotEmpty() == true) {
        stickyHeader {
            Text("Attributions", style = MaterialTheme.typography.titleMedium)
            Divider()
        }
        indirectContributors.let {
            item { ThirdPartyContributorFlowRow(thirdPartyContributors = it) }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun DirectContributorFlowRow(
    modifier: Modifier = Modifier,
    directContributors: List<ContributorDirect>
) {
    FlowRow(modifier = modifier.padding(vertical = 4.dp)) {
        directContributors.forEach {
            Card {
                Text(
                    text = "@${it.username}",
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalLayoutApi::class)
private fun ThirdPartyContributorFlowRow(
    modifier: Modifier = Modifier,
    thirdPartyContributors: List<ContributorAttribution>
) {
    FlowRow(modifier = modifier.padding(vertical = 4.dp)) {
        thirdPartyContributors.forEach { contributor ->
            Card {
                Column(modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = contributor.ownerName,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    contributor.additionalInfo[ContributorInfoKey.USAGE_INFO]?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    contributor.additionalInfo[ContributorInfoKey.LICENSE]?.let {
                        Text(text = it, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Preview(group = "Night", showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(group = "Day", showSystemUi = true, uiMode = UI_MODE_NIGHT_NO)
annotation class PreviewWrapper()

@PreviewWrapper
@Composable
private fun ContributorFlowLayouts_Preview() {
    AndroidPlaygroundTheme {
        AboutPageLayout(
            directContributors = Contributors.values().map { it.toModel() },
            indirectContributors = Attributions.values().map { it.toModel() }
        )
    }
}
