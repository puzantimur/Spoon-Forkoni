package com.example.homew3.domain.repository

import com.example.homew3.domain.model.*

interface RecipeRepository {

    suspend fun getRecipes(ingredients: String): Result<List<Recipe>>

    suspend fun getSteps(id: String): Result<List<General>>

    suspend fun getInfo(id: String): Result<InfoRecipe>

    suspend fun getAll(): List<Recipe>

    suspend fun insertAll(recipes: List<Recipe>)

    suspend fun deleteAll(list: List<Recipe>)

    suspend fun finById(id: Int): Recipe

    suspend fun loginUser(login: String, password: String): Result<User>

    suspend fun registryUser(user: User): User

    suspend fun validateUser(login:String, email:String):Result<User>

    suspend fun getUserById(id: String): Result<User>

    suspend fun deleteUser(user: User)

    suspend fun updateShopList(list: List<ExtendedIngredient>, id: String)

    suspend fun updateFavorite(list: List<Recipe>, id:String)



}
