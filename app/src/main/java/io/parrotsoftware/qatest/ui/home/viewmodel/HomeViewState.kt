package io.parrotsoftware.qatest.ui.home.viewmodel

import io.parrotsoftware.qatest.models.entities.ExpandableCategory

sealed class HomeViewState {
    object Loading: HomeViewState()
    class ErrorLoadingItems(val categories: List<ExpandableCategory>) : HomeViewState()
    object ErrorUpdatingItem : HomeViewState()
    object ItemUpdated : HomeViewState()
    class ItemsLoaded(val categories: List<ExpandableCategory>) : HomeViewState()
}