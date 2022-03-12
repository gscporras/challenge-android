package io.parrotsoftware.qatest.persistence

import androidx.room.*
import io.parrotsoftware.qatest.models.entities.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProducts(categories: List<Product>)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM PRODUCT")
    suspend fun getProducts(): List<Product>
}