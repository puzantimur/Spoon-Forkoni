package com.example.homew3.MVVM.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.MVVM.Model.Recipe
import com.example.homew3.MVVM.Model.RecipesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

class RecipesViewModel(
    private val recipesApi: RecipesApi
): ViewModel() {

    private val refreshFlow = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val queryFlow = MutableStateFlow("")


    val recipeFlow: Flow<List<Recipe>> = queryFlow.combine(
        refreshFlow
            .onStart { emit(Unit) }
            .map { recipesApi.getRecipes("26fdb210da4142409671f31104a8ef9f",
            "meat",
            99) }
    ) {query, recipes ->
        recipes.filter { it.title.contains(query, ignoreCase = true) }
    }.shareIn(
        viewModelScope,
        SharingStarted.Eagerly,
        replay = 1
    )

    fun onQueryChanged(query:String) {
        queryFlow.value = query
    }

    fun onRefreshedRecipes() {
        refreshFlow.tryEmit(Unit)
    }
}
