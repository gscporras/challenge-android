package io.parrotsoftware.qatest.ui.custom

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.parrotsoftware.qatest.R
import io.parrotsoftware.qatest.ui.theme.black
import io.parrotsoftware.qatest.ui.theme.white

@Composable
@Preview
fun ParrotButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier
            .height(48.dp),
        onClick = { onClick.invoke() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = black
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = stringResource(id = R.string.login_button),
            color = white,
            style = MaterialTheme.typography.body1
        )
    }
}