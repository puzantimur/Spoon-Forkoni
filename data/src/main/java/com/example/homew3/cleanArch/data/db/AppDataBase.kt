package com.example.homew3.cleanArch.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.homew3.cleanArch.data.db.converters.ExtendedIngredientConverter
import com.example.homew3.cleanArch.data.db.converters.InfoRecipeConverter
import com.example.homew3.cleanArch.data.db.converters.MissedIngredientsConverter
import com.example.homew3.cleanArch.data.db.converters.RecipeEntitiesConverter
import com.example.homew3.cleanArch.data.db.dao.RecipesDao
import com.example.homew3.cleanArch.data.db.dao.UserDao
import com.example.homew3.cleanArch.data.model.entity.RecipesEntity
import com.example.homew3.cleanArch.data.model.entity.UserEntity

@Database(
    entities = [
        RecipesEntity::class,
        UserEntity::class,
    ],
    version = 1
)
@TypeConverters(
    MissedIngredientsConverter::class,
    InfoRecipeConverter::class,
    RecipeEntitiesConverter::class,
    ExtendedIngredientConverter:: class

)
internal abstract class AppDataBase : RoomDatabase() {

    abstract val recipesDao: RecipesDao

    abstract val userDao: UserDao

}
