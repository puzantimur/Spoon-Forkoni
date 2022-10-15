package com.example.homew3.cleanArch.data.di

import android.app.Application
import androidx.room.Room
import com.example.homew3.cleanArch.data.db.AppDataBase
import com.example.homew3.cleanArch.data.db.RecipesDao
import org.koin.dsl.module

internal val roomModule = module {

    single {
        Room.databaseBuilder(
            get(),
            AppDataBase::class.java,
            "recipeDatabase"
        ).build()
    }

    single { get<AppDataBase>().recipesDao }
}
