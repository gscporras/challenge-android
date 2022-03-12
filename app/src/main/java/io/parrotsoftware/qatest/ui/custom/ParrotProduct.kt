package io.parrotsoftware.qatest.ui.custom

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.parrotsoftware.qatest.R
import io.parrotsoftware.qatest.models.entities.EnabledProduct
import io.parrotsoftware.qatest.ui.theme.black50
import io.parrotsoftware.qatest.ui.theme.orange

@Composable
fun ParrotProduct(
    product: EnabledProduct,
    onProductSelected: (EnabledProduct) -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(
                top = 8.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 16.dp
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = product.product.name,
                color = black50,
                style = MaterialTheme.typography.body2
            )

            Text(
                text = context.getString(R.string.price_product, product.product.price),
                color = black50,
                style = MaterialTheme.typography.body2
            )
        }

        Switch(
            checked = product.enabled,
            onCheckedChange = {
                onProductSelected(product)
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = orange,
                uncheckedThumbColor = Color.Gray,
                checkedTrackColor = Color.Gray,
                uncheckedTrackColor = Color.DarkGray
            )
        )
    }
}