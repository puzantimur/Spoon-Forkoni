package com.example.homew3.cleanArch.data.api

import com.example.homew3.cleanArch.data.model.GeneralDTO
import com.example.homew3.cleanArch.data.model.RecipesDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RecipesApi {

    @GET("recipes/findByIngredients")
    suspend fun getRecipes(
        @Query("apiKey") key: String,
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int
    ): List<RecipesDTO>

    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getSteps(
        @Path("id") id: String,
        @Query("apiKey") key: String
    ): List<GeneralDTO>
}
