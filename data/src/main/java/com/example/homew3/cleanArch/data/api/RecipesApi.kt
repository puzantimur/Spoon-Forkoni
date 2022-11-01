package com.example.homew3.cleanArch.data.api

import com.example.homew3.cleanArch.data.model.dto.GeneralDTO
import com.example.homew3.cleanArch.data.model.dto.InfoRecipesDTO
import com.example.homew3.cleanArch.data.model.dto.RecipesDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RecipesApi {

    @GET("recipes/findByIngredients")
    suspend fun getRecipes(
        @Query("apiKey") key: String,
        @Query("ingredients") ingredients: String,
        @Query("number") recipesPerRequest: Int
    ): List<RecipesDTO>

    @GET("recipes/{id}/analyzedInstructions")
    suspend fun getSteps(
        @Path("id") id: String,
        @Query("apiKey") key: String
    ): List<GeneralDTO>

    @GET("recipes/{id}/information")
    suspend fun getInfo(
        @Path("id") id: String,
        @Query("apiKey") key: String
    ): InfoRecipesDTO
}
