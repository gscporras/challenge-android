package io.parrotsoftware.qatest.persistence.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.parrotsoftware.qatest.models.entities.Product

open class ProductListConverter {
    @TypeConverter
    fun fromString(value: String): List<Product>? {
        val listType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson<List<Product>>(value, listType)
    }

    @TypeConverter
    fun fromList(result: List<Product>?): String {
        val gson = Gson()
        return gson.toJson(result)
    }
}