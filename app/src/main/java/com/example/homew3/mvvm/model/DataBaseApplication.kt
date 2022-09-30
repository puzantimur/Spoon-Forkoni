package com.example.homew3.mvvm.model

import android.app.Application
import android.content.Context
import androidx.room.Room

class DataBaseApplication : Application() {

    var database: AppDataBase? = null

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDataBase::class.java,
            "recipesDataBase"
        ).build()
    }
}

val Context.appDataBase: AppDataBase
    get() = when (this) {
        is DataBaseApplication -> requireNotNull(database)
        else -> applicationContext.appDataBase
    }
