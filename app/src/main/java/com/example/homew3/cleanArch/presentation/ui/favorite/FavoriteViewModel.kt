package com.example.homew3.cleanArch.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.domain.model.MissedIngredients
import com.example.homew3.domain.model.Recipe
import com.example.homew3.domain.model.User
import com.example.homew3.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val recipeRepository: RecipeRepository,
    private val id: String
) : ViewModel() {

    private val _favoriteFlow = MutableStateFlow<Lce<List<Recipe>>>(Lce.Loading)

    val favoriteFlow: Flow<Lce<List<Recipe>>> = _favoriteFlow.asStateFlow()

    init {
        viewModelScope.launch {
            recipeRepository.getUserById(id).onSuccess {
                _favoriteFlow.value = Lce.Content(it.favorite)
            }.onFailure {
                _favoriteFlow.value = Lce.Error(it)
            }
        }
    }


    fun onClickedOk(recipe: Recipe) {
        viewModelScope.launch {
            recipeRepository.getUserById(id).onSuccess {
                val lastList = it.favorite.toMutableList()
                lastList.remove(recipe)
                recipeRepository.updateFavorite(lastList, it.id)
            }
        }
    }




}