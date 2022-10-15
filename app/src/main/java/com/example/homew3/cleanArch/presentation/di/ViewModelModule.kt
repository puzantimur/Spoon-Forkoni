package com.example.homew3.cleanArch.presentation.di

import com.example.homew3.cleanArch.presentation.ui.recipes.RecipesViewModel
import com.example.homew3.cleanArch.presentation.ui.stepByStep.StepsViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { (key: String) -> RecipesViewModel(get(), get(), key) }
}
val stepViewModelModule = module {
    single { (id: String) -> StepsViewModel(get(), id) }
}
