package com.example.homew3.cleanArch.data.repository

import com.example.homew3.cleanArch.data.api.RecipesApi
import com.example.homew3.cleanArch.data.db.dao.RecipesDao
import com.example.homew3.cleanArch.data.db.dao.UserDao
import com.example.homew3.cleanArch.data.mapper.toDomain
import com.example.homew3.cleanArch.data.mapper.toDomainModel
import com.example.homew3.cleanArch.data.mapper.toEntity
import com.example.homew3.cleanArch.data.mapper.toListRecipeEntity
import com.example.homew3.domain.model.*
import com.example.homew3.domain.repository.RecipeRepository

internal class RecipeRepositoryImpl(
    private val recipesApi: RecipesApi,
    private val recipesDao: RecipesDao,
    private val userDao: UserDao
) : RecipeRepository {
    override suspend fun getRecipes(ingredients: String): Result<List<Recipe>> {
        return runCatching {
            recipesApi.getRecipes(
                API_KEY,
                ingredients,
                COUNT_RECIPES_PER_REQUEST
            )
                .toDomainModel()
        }
    }

    override suspend fun getSteps(id: String): Result<List<General>> {
        return runCatching {
            recipesApi.getSteps(
                id,
                API_KEY
            )
                .toDomainModel()
        }
    }

    override suspend fun getInfo(id: String): Result<InfoRecipe> {
        return runCatching {
            recipesApi.getInfo(
                id,
                API_KEY
            )
                .toDomain()
        }
    }

    override suspend fun insertAll(recipes: List<Recipe>) {
        recipesDao.insertAll(recipes.map { it.toEntity() })
    }

    override suspend fun deleteAll(list: List<Recipe>) {
        recipesDao.deleteAll(list.map { it.toEntity() })
    }

    override suspend fun finById(id: Int): Recipe {
        return recipesDao.findById(id).toDomain()
    }

    override suspend fun loginUser(login: String, password: String): Result<User> {
        return runCatching { userDao.loginUser(login, password).toDomain() }
    }

    override suspend fun registryUser(user: User): User {
        userDao.insertUser(user.toEntity())
        return user
    }

    override suspend fun validateUser(login: String, email: String): Result<User> {
        return runCatching { userDao.findByLoginOrEmail(login, email).toDomain() }
    }

    override suspend fun getUserById(id: String): Result<User> {
        return runCatching { userDao.getUserById(id).toDomain() }
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user.toEntity())
    }

    override suspend fun updateShopList(list: List<ExtendedIngredient>, id:String) {
        userDao.updateShopList(list, id)
    }

    override suspend fun updateFavorite(list: List<Recipe>, id:String) {
        userDao.updateFavorite(list.toListRecipeEntity(), id)
    }

    override suspend fun getAll(): List<Recipe> {
        return recipesDao.getAll().map { it.toDomain() }
    }

    companion object {
        private const val API_KEY = "b422591fa8f247039650eb75b5804246"
        private const val COUNT_RECIPES_PER_REQUEST = 50
    }
}
