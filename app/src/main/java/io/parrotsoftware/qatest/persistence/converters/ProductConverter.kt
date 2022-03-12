package io.parrotsoftware.qatest.persistence.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.parrotsoftware.qatest.models.entities.Product

open class ProductConverter {
    @TypeConverter
    fun fromString(value: String): Product? {
        val listType = object : TypeToken<Product>() {}.type
        return Gson().fromJson<Product>(value, listType)
    }

    @TypeConverter
    fun fromList(result: Product?): String {
        val gson = Gson()
        return gson.toJson(result)
    }
}