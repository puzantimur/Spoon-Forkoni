package com.example.homew3.cleanArch.data.db.dao

import androidx.room.*
import com.example.homew3.cleanArch.data.model.entity.RecipesEntity

@Dao
internal interface RecipesDao {

    @Query("SELECT * FROM recipesentity")
    suspend fun getAll(): List<RecipesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(recipes: List<RecipesEntity>)

    @Delete
    suspend fun deleteAll(recipes: List<RecipesEntity>)

    @Query("SELECT * FROM recipesentity WHERE id LIKE :id")
    suspend fun findById(id: Int): RecipesEntity
}
