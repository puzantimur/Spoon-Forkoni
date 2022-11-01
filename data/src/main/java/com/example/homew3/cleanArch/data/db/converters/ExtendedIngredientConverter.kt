package com.example.homew3.cleanArch.data.db.converters

import androidx.room.TypeConverter
import com.example.homew3.domain.model.ExtendedIngredient
import com.example.homew3.domain.model.InfoRecipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class ExtendedIngredientConverter {
    @TypeConverter
    fun fromExList(value: List<ExtendedIngredient>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toExList(value: String): List<ExtendedIngredient> {
        val gson = Gson()
        val type = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return gson.fromJson(value, type)
    }
}