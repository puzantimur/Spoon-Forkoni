package com.example.homew3.cleanArch.data.db

import androidx.room.TypeConverter
import com.example.homew3.domain.model.MissedIngredients
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

internal class DataConverter : Serializable {
    @TypeConverter
    fun fromIngredientsList(value: Array<MissedIngredients>): String {
        val gson = Gson()
        val type = object : TypeToken<Array<MissedIngredients>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toIngredientsList(value: String): Array<MissedIngredients> {
        val gson = Gson()
        val type = object : TypeToken<Array<MissedIngredients>>() {}.type
        return gson.fromJson(value, type)
    }
}
