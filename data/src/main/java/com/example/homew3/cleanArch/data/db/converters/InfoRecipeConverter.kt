package com.example.homew3.cleanArch.data.db.converters

import androidx.room.TypeConverter
import com.example.homew3.domain.model.InfoRecipe
import com.example.homew3.domain.model.MissedIngredients
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

internal class InfoRecipeConverter {
    @TypeConverter
    fun fromInfoList(value: List<InfoRecipe>): String {
        val gson = Gson()
        val type = object : TypeToken<List<InfoRecipe>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toInfoList(value: String): List<InfoRecipe> {
        val gson = Gson()
        val type = object : TypeToken<List<InfoRecipe>>() {}.type
        return gson.fromJson(value, type)
    }
}