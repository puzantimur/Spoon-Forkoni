package com.example.homew3.cleanArch.data.repository

import com.example.homew3.cleanArch.data.api.RecipesApi
import com.example.homew3.cleanArch.data.mapper.toDomainModel
import com.example.homew3.domain.model.General
import com.example.homew3.domain.model.Recipe
import com.example.homew3.domain.repository.ApiRecipeRepository

internal class ApiRecipeRepositoryImpl(
    private val recipesApi: RecipesApi
) : ApiRecipeRepository {
    override suspend fun getRecipes(key: String): Result<List<Recipe>> {
        return runCatching {
            recipesApi.getRecipes(
                "b422591fa8f247039650eb75b5804246",
                key, //как сюда можно передать строку с фрагмента?
                99
            )
                .toDomainModel()
        }
    }

    override suspend fun getSteps(id: String): Result<List<General>> {
        return runCatching {
            recipesApi.getSteps(
                id,
                "b422591fa8f247039650eb75b5804246"
            )
                .toDomainModel()
        }
    }
}
