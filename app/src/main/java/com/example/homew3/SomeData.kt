package com.example.homew3

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object SomeData {
    private const val SPOON_URL = "https://api.spoonacular.com/"

    private val paddinBetweenObjects: Int = 50

    val getData get() = paddinBetweenObjects

    val api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(SPOON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create<RecipesApi>()
    }
}
