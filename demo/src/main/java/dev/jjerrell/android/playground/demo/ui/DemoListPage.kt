/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jjerrell.android.playground.base.android.navigation.BasePlaygroundNavigation
import dev.jjerrell.android.playground.demo.navigation.DemoNavigationGroup

/**
 * Simple page for linking to any visualized demos
 *
 * @param modifier the [Modifier] applied to this composables' top-level composable
 * @param onRequestDemo action to take when a user requests one of the demo items
 * @receiver
 */
@Composable
fun DemoListPage(modifier: Modifier = Modifier, onRequestDemo: (BasePlaygroundNavigation) -> Unit) {
    LazyVerticalGrid(modifier = modifier.padding(8.dp), columns = GridCells.Adaptive(156.dp)) {
        items(DemoNavigationGroup.pages.filterNot { it is DemoNavigationGroup.Home }) {
            Button(onClick = { onRequestDemo(it) }) { Text("Logging") }
        }
    }
}

@Preview
@Composable
private fun DemoListPage_Preview() {
    DemoListPage(onRequestDemo = {})
}
