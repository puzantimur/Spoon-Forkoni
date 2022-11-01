package com.example.homew3.cleanArch.data.db.converters

import androidx.room.TypeConverter
import com.example.homew3.domain.model.MissedIngredients
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

internal class MissedIngredientsConverter : Serializable {
    @TypeConverter
    fun fromIngredientsList(value: List<MissedIngredients>): String {
        val gson = Gson()
        val type = object : TypeToken<List<MissedIngredients>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIngredientsList(value: String): List<MissedIngredients> {
        val gson = Gson()
        val type = object : TypeToken<List<MissedIngredients>>() {}.type
        return gson.fromJson(value, type)
    }
}
