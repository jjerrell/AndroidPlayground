/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.demo.ui

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

@Composable
fun DemoListPage(modifier: Modifier = Modifier, onRequestDemo: (BasePlaygroundNavigation) -> Unit) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Adaptive(156.dp)) {
        items(DemoNavigationGroup.pages.filterNot { it is DemoNavigationGroup.Home }) {
            when (it) {
                DemoNavigationGroup.Home -> return@items
                else -> Button(onClick = { onRequestDemo(it) }) { Text("Logging") }
            }
        }
    }
}

@Preview
@Composable
private fun DemoListPage_Preview() {
    DemoListPage(onRequestDemo = {})
}
