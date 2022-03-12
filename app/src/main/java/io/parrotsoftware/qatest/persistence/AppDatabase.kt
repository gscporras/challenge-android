package io.parrotsoftware.qatest.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.parrotsoftware.qatest.models.entities.*
import io.parrotsoftware.qatest.persistence.converters.*

@Database(
    entities = [
        (Credentials::class),
        (Store::class),
        (Product::class)
    ],
    version = 3, exportSchema = false
)
@TypeConverters(
    value = [
        (ProductListConverter::class),
        (StoreListConverter::class),
        (CategoryConverter::class),
        (ProductConverter::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authDao(): AuthDao
    abstract fun productDao(): ProductDao
}