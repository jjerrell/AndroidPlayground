/* (C) 2023 Jacob Jerrell */
package dev.jjerrell.android.playground.feature.welcome.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.jjerrell.android.playground.base.android.theme.AndroidPlaygroundTheme

@Composable
fun WelcomePage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Android Playground")
    }
}

@Composable
fun Block(modifier: Modifier = Modifier) {
    Box(
        modifier =
            modifier
                .requiredSize(width = 25.dp, height = 25.dp)
                .clip(CutCornerShape(25.dp))
                .border(Dp.Hairline, Color.Black, shape = CutCornerShape(25.dp))
                .clipToBounds()
    )
}

@Preview(showBackground = true)
@Composable
fun Welcome_Preview() {
    AndroidPlaygroundTheme { WelcomePage() }
}
