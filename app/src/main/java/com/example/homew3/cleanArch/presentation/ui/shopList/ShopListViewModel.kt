package com.example.homew3.cleanArch.presentation.ui.shopList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.domain.model.ExtendedIngredient
import com.example.homew3.domain.model.MissedIngredients
import com.example.homew3.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.math.E

class ShopListViewModel(
    private val recipeRepository: RecipeRepository,
    context: Context
) : ViewModel() {

    private val prefsManager by lazy {
        SharedPreferencesManager(context)
    }


    private val _shopListFlow = MutableStateFlow<Lce<List<ExtendedIngredient>>>(Lce.Loading)

    val shopListFlow: Flow<Lce<List<ExtendedIngredient>>> = _shopListFlow.asStateFlow()

//    val a: StateFlow<Lce<List<ExtendedIngredient>>> = flow{
//        recipeRepository.getUserById(prefsManager.userId).onSuccess {
//            emit(Lce.Content(it.shopList))
//        }.onFailure {
//            emit(Lce.Error(it))
//        }
//    }
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.Eagerly,
//            initialValue = Lce.Loading
//        )

    init {
        viewModelScope.launch {
            recipeRepository.getUserById(prefsManager.userId).onSuccess {
                _shopListFlow.value = Lce.Content(it.shopList)
            }
                .onFailure {
                    _shopListFlow.value = Lce.Error(it)
                }
        }
    }

}