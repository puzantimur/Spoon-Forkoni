package com.example.homew3.cleanArch.presentation.ui.missedIngredients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.domain.model.MissedIngredients
import com.example.homew3.domain.model.Recipe
import com.example.homew3.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MissedIngredientViewModel(
    private val recipeRepository: RecipeRepository,
    private val id: String
) : ViewModel() {

    private val _missedFlow = MutableStateFlow<Lce<List<MissedIngredients>>>(Lce.Loading)

    val missedFlow: Flow<Lce<List<MissedIngredients>>> = _missedFlow.asStateFlow()

    init {
        viewModelScope.launch {
            val missedIngredients = recipeRepository.finById(id.toInt()).missedIngredients
            _missedFlow.value = Lce.Content(missedIngredients)
        }
    }

}