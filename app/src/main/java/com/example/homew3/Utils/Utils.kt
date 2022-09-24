package com.example.homew3.Utils

import com.example.homew3.MVVM.Model.RecipesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Utils {
    private const val SPOON_URL = "https://api.spoonacular.com/"

    val api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(SPOON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create<RecipesApi>()
    }
}
