package io.parrotsoftware.qatest.ui.home.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.parrotsoftware.qatest.models.entities.EnabledProduct
import io.parrotsoftware.qatest.models.entities.ExpandableCategory
import io.parrotsoftware.qatest.models.entities.ProductAvailability
import io.parrotsoftware.qatest.repository.AuthRepository
import io.parrotsoftware.qatest.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val productRepository: ProductRepository
): ViewModel() {

    private val _viewState = MutableLiveData<HomeViewState>()
    fun getViewState() = _viewState

    val isLoading: LiveData<Boolean> = Transformations.map(_viewState) {
        it is HomeViewState.Loading
    }

    private val categoriesExpanded = productRepository.categoriesExpanded

    private val _storeName = mutableStateOf("")
    val storeName: State<String> get() = _storeName

    init {
        getStoreName()
        fetchProducts()
    }

    private fun getStoreName() {
        viewModelScope.launch {
            _storeName.value = authRepository.getStoreName() ?: ""
        }
    }

    fun fetchProducts() {
        _viewState.value = HomeViewState.Loading
        viewModelScope.launch {
            val result = productRepository.fetchProduct()
            _viewState.value = result
        }
    }

    private fun updateProduct(productId: String, newState: ProductAvailability) {
        viewModelScope.launch {
            val result = productRepository.updateProduct(productId, newState)
            _viewState.value = result
        }
    }

    fun categorySelected(category: ExpandableCategory) {
        val currentState = categoriesExpanded[category.category.uuid] ?: false
        categoriesExpanded[category.category.uuid] = !currentState
        val expandedCategories = productRepository.createCategoriesList()
        _viewState.value = HomeViewState.ItemsLoaded(expandedCategories)
    }

    fun productSelected(product: EnabledProduct) {
        _viewState.value = productRepository.productSelected(product)
        updateProduct(product.product.uuid, productRepository.getProductAvailability(product))
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}