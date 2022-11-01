package com.example.homew3.cleanArch.presentation.di

import com.example.homew3.cleanArch.presentation.ui.favorite.FavoriteViewModel
import com.example.homew3.cleanArch.presentation.ui.info.InfoViewModel
import com.example.homew3.cleanArch.presentation.ui.missedIngredients.MissedIngredientViewModel
import com.example.homew3.cleanArch.presentation.ui.moreIfno.MoreInfoViewModel
import com.example.homew3.cleanArch.presentation.ui.profile.ProfileViewModel
import com.example.homew3.cleanArch.presentation.ui.profile.registryLogin.LoginViewModel
import com.example.homew3.cleanArch.presentation.ui.profile.registryLogin.RegistryViewModel
import com.example.homew3.cleanArch.presentation.ui.recipes.RecipesViewModel
import com.example.homew3.cleanArch.presentation.ui.shopList.ShopListViewModel
import com.example.homew3.cleanArch.presentation.ui.stepByStep.StepsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::ShopListViewModel)
    viewModelOf(::FavoriteViewModel)
    viewModelOf(::MoreInfoViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::RecipesViewModel)
    viewModelOf(::StepsViewModel)
    viewModelOf(::InfoViewModel)
    viewModelOf(::MissedIngredientViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegistryViewModel)
}
