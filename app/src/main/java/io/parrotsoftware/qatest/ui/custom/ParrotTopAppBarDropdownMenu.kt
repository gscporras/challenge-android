package io.parrotsoftware.qatest.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.parrotsoftware.qatest.R
import io.parrotsoftware.qatest.ui.theme.black
import io.parrotsoftware.qatest.ui.theme.white

@Composable
@Preview
fun ParrotTopAppBarDropdownMenu(
    bodyContent: MutableState<String> = mutableStateOf(""),
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val expanded = remember { mutableStateOf(false) }

    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true
            bodyContent.value =  "Menu Opening"
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More Menu",
                tint = white
            )
        }
    }

    DropdownMenu(
        modifier = Modifier
            .background(black),
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        DropdownMenuItem(
            modifier = Modifier
                .background(black),
            onClick = {
                expanded.value = false
                bodyContent.value = "First Item Selected"
                onClick.invoke()
            }
        ) {
            Text(
                context.getString(R.string.logout),
                color = white,
                style = MaterialTheme.typography.body1
            )
        }
    }
}