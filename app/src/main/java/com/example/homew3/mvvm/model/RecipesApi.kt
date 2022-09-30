package com.example.homew3.mvvm.model

import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {

    @GET("recipes/findByIngredients")
    suspend fun getRecipes(
        @Query("apiKey") key: String,
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int
    ): List<Recipe>
}
