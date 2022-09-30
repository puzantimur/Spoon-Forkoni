package com.example.homew3.mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.mvvm.model.Recipe
import com.example.homew3.mvvm.model.RecipesApi
import com.example.homew3.mvvm.model.RecipesDao
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class RecipesViewModel(
    private val recipesApi: RecipesApi,
    private val recipeDao: RecipesDao
) : ViewModel() {

    private val refreshFlow = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val queryFlow = MutableStateFlow("")

    val recipeFlow: Flow<List<Recipe>> = queryFlow.combine(
        refreshFlow
            .map {
                runCatching {
                    recipesApi.getRecipes(
                        "b422591fa8f247039650eb75b5804246",
                        "meat",
                        99
                    )
                }
            }
    ) { query, result ->
        result.fold(onSuccess = { onSuccess ->
            recipeDao.insertAll(onSuccess)
            onSuccess.filter { it.title.contains(query, ignoreCase = true) }
        }, onFailure = {
            emptyList()
        })
    }
        .onStart { emit(recipeDao.getAll()) }
        .shareIn(
            viewModelScope,
            SharingStarted.Eagerly,
            replay = 1
        )

    fun onQueryChanged(query: String) {
        queryFlow.value = query
    }

    fun onRefreshedRecipes() {
        refreshFlow.tryEmit(Unit)
    }

}
