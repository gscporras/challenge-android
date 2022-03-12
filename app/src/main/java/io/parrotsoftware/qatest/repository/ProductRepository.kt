package io.parrotsoftware.qatest.repository

import io.parrotsoftware.qatest.models.entities.*
import io.parrotsoftware.qatest.models.network.request.UpdateProductRequest
import io.parrotsoftware.qatest.models.network.safeApiCall
import io.parrotsoftware.qatest.network.service.ProductService
import io.parrotsoftware.qatest.persistence.AuthDao
import io.parrotsoftware.qatest.persistence.ProductDao
import io.parrotsoftware.qatest.ui.home.viewmodel.HomeViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository constructor(
    private val productService: ProductService,
    private val authDao: AuthDao,
    private val productDao: ProductDao
): Repository {

    private var products = mutableListOf<Product>()
    val categoriesExpanded = mutableMapOf<String, Boolean>()

    suspend fun updateProduct(productId: String, newState: ProductAvailability): HomeViewState =
        withContext(
            Dispatchers.IO
        ) {
            val access = authDao.getCredentials()?.access ?: ""
            val response = safeApiCall {
                productService.updateProduct(
                    "Bearer $access",
                    productId,
                    UpdateProductRequest(newState)
                )
            }

            if (response.isError) {
                return@withContext HomeViewState.ErrorUpdatingItem
            }

            val product = response.requiredResult.result
            productDao.updateProduct(product)
            return@withContext HomeViewState.ItemUpdated
        }

    suspend fun fetchProduct(): HomeViewState =
        withContext(
            Dispatchers.IO
        ) {
            val access = authDao.getCredentials()?.access ?: ""
            val storeId = authDao.getStore()?.uuid ?: ""

            products = productDao.getProducts().toMutableList()

            val response = safeApiCall {
                productService.getProducts("Bearer $access", storeId)
            }

            if (response.isError) {
                val expandedCategories = createCategoriesList()
                return@withContext HomeViewState.ErrorLoadingItems(expandedCategories)
            }

            products = response.requiredResult.results.toMutableList()
            val expandedCategories = createCategoriesList()
            productDao.saveProducts(products)
            return@withContext HomeViewState.ItemsLoaded(expandedCategories)
        }

    fun createCategoriesList(): List<ExpandableCategory> {
        val categories = products
            .map { it.category }
            .distinctBy { it.uuid }
            .sortedBy { it.sortPosition }
        val groupedProducts = products.groupBy { it.category.uuid }

        return categories.map { category ->
            val products = groupedProducts[category.uuid]?.map { product ->
                EnabledProduct(product, product.availability == ProductAvailability.AVAILABLE)
            } ?: emptyList()

            ExpandableCategory(
                category,
                categoriesExpanded[category.uuid] ?: false,
                products
            )
        }
    }

    fun productSelected(product: EnabledProduct): HomeViewState {
        val index = products.indexOfFirst { it.uuid == product.product.uuid }
        products[index] = product.product.copy(availability = getProductAvailability(product))
        val expandedCategories = createCategoriesList()
        return HomeViewState.ItemsLoaded(expandedCategories)
    }


    fun getProductAvailability(product: EnabledProduct): ProductAvailability {
        return if (product.product.availability == ProductAvailability.AVAILABLE)
            ProductAvailability.UNAVAILABLE
        else
            ProductAvailability.AVAILABLE
    }
}