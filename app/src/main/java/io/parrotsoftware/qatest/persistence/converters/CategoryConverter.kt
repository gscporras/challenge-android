package io.parrotsoftware.qatest.persistence.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.parrotsoftware.qatest.models.entities.Category

open class CategoryConverter {
    @TypeConverter
    fun fromString(value: String): Category? {
        val listType = object : TypeToken<Category>() {}.type
        return Gson().fromJson<Category>(value, listType)
    }

    @TypeConverter
    fun fromList(result: Category?): String {
        val gson = Gson()
        return gson.toJson(result)
    }
}