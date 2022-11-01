package com.example.homew3.cleanArch.data.db.converters

import androidx.room.TypeConverter
import com.example.homew3.cleanArch.data.model.entity.RecipesEntity
import com.example.homew3.domain.model.MissedIngredients
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable

internal class RecipeEntitiesConverter: Serializable {
    @TypeConverter
    fun fromRecipeList(value: List<RecipesEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<RecipesEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toRecipeList(value: String): List<RecipesEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<RecipesEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}