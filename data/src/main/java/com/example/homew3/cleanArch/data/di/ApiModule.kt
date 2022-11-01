package com.example.homew3.cleanArch.data.di

import com.example.homew3.cleanArch.data.api.RecipesApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal val apiModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(ApiUtils.SPOON_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(RecipesApi::class.java)
    }
}

internal object ApiUtils {
    const val SPOON_URL = "https://api.spoonacular.com/"
}
