package io.parrotsoftware.qatest.persistence

import androidx.room.*
import io.parrotsoftware.qatest.models.entities.Credentials
import io.parrotsoftware.qatest.models.entities.Store

@Dao
interface AuthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCredentials(credentials: Credentials)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStore(store: Store)

    @Query("DELETE FROM CREDENTIALS")
    suspend fun deleteCredentials()

    @Query("DELETE FROM STORE")
    suspend fun deleteStore()

    @Query("SELECT * FROM CREDENTIALS")
    suspend fun getCredentials(): Credentials?

    @Query("SELECT * FROM STORE")
    suspend fun getStore(): Store?
}