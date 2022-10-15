package com.example.homew3.cleanArch.data.repository

import com.example.homew3.cleanArch.data.db.RecipesDao
import com.example.homew3.cleanArch.data.mapper.toDomains
import com.example.homew3.cleanArch.data.mapper.toEntity
import com.example.homew3.domain.model.Recipe
import com.example.homew3.domain.repository.DBRepository

internal class DBRepositoryImpl(
    private val recipesDao: RecipesDao
) : DBRepository {

    override suspend fun insertAll(recipes: List<Recipe>) {
        recipesDao.insertAll(recipes.map { it.toEntity() })
    }

    override suspend fun deleteAll(list: List<Recipe>) {
        recipesDao.deleteAll(list.map { it.toEntity() })
    }

    override suspend fun getAll(): List<Recipe> {
        return recipesDao.getAll().map { it.toDomains() }
    }
}
