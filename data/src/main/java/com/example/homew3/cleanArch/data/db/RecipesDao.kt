package com.example.homew3.cleanArch.data.db

import androidx.room.*
import com.example.homew3.cleanArch.data.model.RecipesEntity
import com.example.homew3.domain.model.Recipe

@Dao
internal interface RecipesDao {

    @Query("SELECT * FROM recipesentity")
    suspend fun getAll(): List<RecipesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipesEntity>)

    @Delete
    suspend fun deleteAll(recipes: List<RecipesEntity>)
}
