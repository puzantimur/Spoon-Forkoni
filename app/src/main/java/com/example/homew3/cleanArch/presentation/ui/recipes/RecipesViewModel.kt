package com.example.homew3.cleanArch.presentation.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.domain.model.Recipe
import com.example.homew3.domain.repository.ApiRecipeRepository
import com.example.homew3.domain.repository.DBRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class RecipesViewModel(
    private val apiRecipeRepository: ApiRecipeRepository,
    private val dbRepository: DBRepository,
    private val key: String
) : ViewModel() {

    private val refreshFlow = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val queryFlow = MutableStateFlow("")

    val recipeFlow: Flow<List<Recipe>> = queryFlow
        .debounce(500)
        .combine(
            refreshFlow
                .map {
                    apiRecipeRepository.getRecipes(key)
                }
        ) { query, result ->
            result.fold(onSuccess = { onSuccess ->
                dbRepository.deleteAll(dbRepository.getAll())
                dbRepository.insertAll(onSuccess)
                onSuccess.filter { it.title.contains(query, ignoreCase = true) }
            }, onFailure = {
                emptyList()
            })
        }
        .onStart { emit(dbRepository.getAll()) }
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

}
