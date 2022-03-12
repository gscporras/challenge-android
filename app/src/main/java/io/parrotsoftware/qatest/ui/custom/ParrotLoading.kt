package io.parrotsoftware.qatest.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.parrotsoftware.qatest.ui.theme.black
import io.parrotsoftware.qatest.ui.theme.black50

@Composable
@Preview
fun ParrotLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(black50)
    ) {

        CircularProgressIndicator(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.Center),
            color = black,
            strokeWidth = 3.dp
        )
    }
}