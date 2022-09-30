package com.example.homew3.mvvm.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract val recipesDao: RecipesDao
}
