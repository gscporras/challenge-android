package io.parrotsoftware.qatest.ui.custom

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import io.parrotsoftware.qatest.R
import io.parrotsoftware.qatest.models.entities.EnabledProduct
import io.parrotsoftware.qatest.models.entities.ExpandableCategory
import io.parrotsoftware.qatest.ui.theme.*

@Composable
fun ParrotCategory(
    categories: List<ExpandableCategory>,
    category: ExpandableCategory,
    index: Int,
    onCategorySelected: (ExpandableCategory) -> Unit,
    onProductSelected: (EnabledProduct) -> Unit
) {

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onCategorySelected(category)
            },
            backgroundColor = white,
            shape = RoundedCornerShape(0.dp),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .animateContentSize(
                        animationSpec = tween(
                            durationMillis = 600,
                            easing = LinearOutSlowInEasing
                        )
                    )
            ) {
                Text(
                    text = category.category.name,
                    color = black,
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = LocalContext.current.resources.getQuantityString(R.plurals.products, category.products.size, category.products.size),
                    color = black50,
                    style = MaterialTheme.typography.body2
                )

                if(category.expanded) {
                    category.products.forEach { product ->
                        ParrotProduct(
                            product = product,
                            onProductSelected = { onProductSelected(it) }
                        )
                    }
                }
            }
        }

        if (index < categories.lastIndex)
            Divider(color = black10, thickness = 1.dp)
    }
}