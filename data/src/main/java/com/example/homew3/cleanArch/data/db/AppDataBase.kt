package com.example.homew3.cleanArch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.homew3.cleanArch.data.model.RecipesEntity
import com.example.homew3.domain.model.Recipe

@Database(entities = [RecipesEntity::class], version = 1)
@TypeConverters(DataConverter::class)
internal abstract class AppDataBase : RoomDatabase() {
    abstract val recipesDao: RecipesDao
}
