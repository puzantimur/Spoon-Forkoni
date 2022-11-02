package com.example.homew3.cleanArch.data.di

import com.example.homew3.cleanArch.data.repository.RecipeRepositoryImpl
import com.example.homew3.domain.repository.RecipeRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val repositoryModule = module {

    singleOf(::RecipeRepositoryImpl) {
        bind<RecipeRepository>()
    }
}
