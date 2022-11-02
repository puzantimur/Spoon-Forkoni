package com.example.homew3.cleanArch.presentation.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.domain.model.Recipe
import com.example.homew3.domain.repository.RecipeRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class RecipesViewModel(
    private val recipeRepository: RecipeRepository,
    private val key: String
) : ViewModel() {

    private val refreshFlow = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val queryFlow = MutableStateFlow("")

    val recipeFlow: Flow<List<Recipe>> = queryFlow
        .debounce(DEBOUNCE)
        .combine(
            refreshFlow
                .map {
                    recipeRepository.getRecipes(key)
                }
        ) { query, result ->
            result.fold(onSuccess = { listRecipe ->
                recipeRepository.deleteAll(recipeRepository.getAll())
                recipeRepository.insertAll(listRecipe)
                listRecipe.filter { it.title.contains(query, ignoreCase = true) }
            }, onFailure = {
                emptyList()
            })
        }
        .onStart { emit(recipeRepository.getAll()) }
        .shareIn(
            viewModelScope,
            SharingStarted.Lazily,
            replay = 1
        )

    fun onQueryChanged(query: String) {
        queryFlow.value = query

    }

    fun onRefreshedRecipes() {
        refreshFlow.tryEmit(Unit)
    }

    companion object{
        private const val DEBOUNCE = 500L
    }

}
