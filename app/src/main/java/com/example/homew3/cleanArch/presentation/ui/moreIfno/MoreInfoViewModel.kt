package com.example.homew3.cleanArch.presentation.ui.moreIfno

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.domain.model.Recipe
import com.example.homew3.domain.model.User
import com.example.homew3.domain.repository.RecipeRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MoreInfoViewModel(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    fun onButtonClicked(idRecipe: Int, idUser: String) {
        viewModelScope.launch {
            recipeRepository.getUserById(idUser).onSuccess {
                val lastList = it.favorite.toMutableList()
                val currentRecipe = recipeRepository.finById(idRecipe)
                lastList.add(currentRecipe)
                lastList.distinct()
                recipeRepository.updateFavorite(lastList, idUser)
                _favoriteFlow.value = Lce.Content(it)
            }.onFailure {
                _favoriteFlow.value = Lce.Error(it)
            }

        }
    }

    private val _favoriteFlow = MutableStateFlow<Lce<User>>(
        Lce.Loading
    )

    val favoriteFlow: Flow<Lce<User>> = _favoriteFlow.asStateFlow()


}
