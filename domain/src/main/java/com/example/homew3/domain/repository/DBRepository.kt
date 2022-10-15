package com.example.homew3.domain.repository

import com.example.homew3.domain.model.Recipe

interface DBRepository {

    suspend fun getAll(): List<Recipe>

    suspend fun insertAll(recipes: List<Recipe>)

    suspend fun deleteAll(list: List<Recipe>)
}
