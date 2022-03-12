package io.parrotsoftware.qatest.persistence.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.parrotsoftware.qatest.models.entities.Store

open class StoreListConverter {
    @TypeConverter
    fun fromString(value: String): List<Store>? {
        val listType = object : TypeToken<List<Store>>() {}.type
        return Gson().fromJson<List<Store>>(value, listType)
    }

    @TypeConverter
    fun fromList(result: List<Store>?): String {
        val gson = Gson()
        return gson.toJson(result)
    }
}