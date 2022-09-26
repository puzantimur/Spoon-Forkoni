package com.example.homew3.MVVM.ViewModel

import com.example.homew3.MVVM.Model.RecipesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceLocator {

    private const val SPOON_URL = "https://api.spoonacular.com/"

    private val recipesApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(SPOON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create<RecipesApi>()
    }

    fun provideRecipes(): RecipesApi = recipesApi
}