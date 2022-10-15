package com.example.homew3.cleanArch.data.di

import com.example.homew3.cleanArch.data.repository.ApiRecipeRepositoryImpl
import com.example.homew3.cleanArch.data.repository.DBRepositoryImpl
import com.example.homew3.domain.repository.ApiRecipeRepository
import com.example.homew3.domain.repository.DBRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {

    singleOf(::ApiRecipeRepositoryImpl) {
        bind<ApiRecipeRepository>()
    }

    singleOf(::DBRepositoryImpl) {
        bind<DBRepository>()
    }
}
