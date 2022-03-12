package io.parrotsoftware.qatest.ui.custom

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.parrotsoftware.qatest.ui.theme.black
import io.parrotsoftware.qatest.ui.theme.white

@Composable
@Preview
fun ParrotTopAppBar(storeName: String = "", onClick: () -> Unit = {}) {
    val bodyContent = remember { mutableStateOf("Body Content Here") }

    TopAppBar(
        modifier = Modifier
            .wrapContentHeight(),
        backgroundColor = black,
        elevation = 0.dp,
        title = {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = storeName,
                color = white,
                style = MaterialTheme.typography.h2
            )
        },
        actions = {
            ParrotTopAppBarDropdownMenu(bodyContent, onClick)
        }
    )
}