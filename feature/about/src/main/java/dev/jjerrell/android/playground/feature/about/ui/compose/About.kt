/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.about.ui.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.jjerrell.android.playground.feature.about.Contributors

@Composable
@ExperimentalFoundationApi
@ExperimentalLayoutApi
@ExperimentalMaterial3Api
internal fun AboutPage() {
    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("About") })
        LazyColumn(
            modifier = Modifier.padding(8.dp).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            item {
                Text(
                    "A simple playground to experiment, implement, and demonstrate various techniques, architectures, and libraries.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            stickyHeader {
                Text("Contributors", style = MaterialTheme.typography.bodyLarge)
                Divider()
            }
            item { FlowRow { Contributors.values().forEach { Card { Text("@${it.username}") } } } }
        }
    }
}