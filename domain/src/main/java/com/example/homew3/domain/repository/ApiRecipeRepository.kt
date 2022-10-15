package com.example.homew3.domain.repository

import com.example.homew3.domain.model.General
import com.example.homew3.domain.model.Recipe

interface ApiRecipeRepository {

    suspend fun getRecipes(key: String): Result<List<Recipe>>

    suspend fun getSteps(id: String): Result<List<General>>
}
