package com.example.homew3

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesApi {

    @GET("recipes/findByIngredients")
    fun getPlayers(
        @Query("apiKey") key : String,
        @Query("ingredients") ingredients : String,
        @Query("number") number : Int) : Call<List<Recipe>>
}

