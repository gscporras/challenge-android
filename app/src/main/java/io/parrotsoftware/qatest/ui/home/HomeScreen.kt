package io.parrotsoftware.qatest.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.parrotsoftware.qatest.R
import io.parrotsoftware.qatest.extensions.showToast
import io.parrotsoftware.qatest.models.entities.ExpandableCategory
import io.parrotsoftware.qatest.ui.custom.ParrotCategory
import io.parrotsoftware.qatest.ui.custom.ParrotTopAppBar
import io.parrotsoftware.qatest.ui.home.viewmodel.HomeViewModel
import io.parrotsoftware.qatest.ui.home.viewmodel.HomeViewState
import io.parrotsoftware.qatest.ui.theme.*

var categories: List<ExpandableCategory> = emptyList()

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToLogin: () -> Unit
) {
    val context = LocalContext.current
    val viewState by viewModel.getViewState().observeAsState()

    when(viewState) {
        is HomeViewState.ErrorLoadingItems -> {
            context.showToast(context.getString(R.string.msg_error_load_products))
            categories = (viewState as HomeViewState.ErrorLoadingItems).categories
        }
        is HomeViewState.ItemsLoaded -> { categories = (viewState as HomeViewState.ItemsLoaded).categories }
        HomeViewState.ErrorUpdatingItem -> context.showToast(context.getString(R.string.msg_error_update_product))
        else -> {}
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeContent(
            storeName = viewModel.storeName.value,
            categories = categories,
            viewModel = viewModel,
            navigateToLogin = navigateToLogin
        )
    }
}

@Composable
fun HomeContent(
    storeName: String = "",
    categories: List<ExpandableCategory> = listOf(),
    viewModel: HomeViewModel,
    navigateToLogin: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = white,
        topBar = {
            ParrotTopAppBar(
                storeName = storeName,
                onClick = {
                    viewModel.signOut()
                    navigateToLogin()
                }
            )
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = viewModel.isLoading.value ?: false),
            onRefresh = { viewModel.fetchProducts() },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = white,
                    shape = CircleShape
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(categories) { index, product ->
                    ParrotCategory(
                        categories = categories,
                        category = product,
                        index = index,
                        onCategorySelected = { viewModel.categorySelected(it) },
                        onProductSelected = { viewModel.productSelected(it) }
                    )
                }
            }
        }
    }
}