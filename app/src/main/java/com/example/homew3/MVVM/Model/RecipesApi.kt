package com.example.homew3.MVVM.Model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {

    @GET("recipes/findByIngredients")
    fun getRecipes(
        @Query("apiKey") key: String,
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int
    ): Call<List<Recipe>>
}
