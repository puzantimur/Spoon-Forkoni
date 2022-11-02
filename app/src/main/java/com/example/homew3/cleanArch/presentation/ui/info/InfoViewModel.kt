package com.example.homew3.cleanArch.presentation.ui.info

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.domain.model.ExtendedIngredient
import com.example.homew3.domain.model.InfoRecipe
import com.example.homew3.domain.repository.RecipeRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class InfoViewModel(
    private val recipeRepository: RecipeRepository,
    context: Context,
    private val id: String
) : ViewModel() {

    private val prefsManager by lazy {
        SharedPreferencesManager(context)
    }

    private val _infoFlow = MutableStateFlow<Lce<InfoRecipe>>(Lce.Loading)

    val infoFlow: Flow<Lce<InfoRecipe>> = _infoFlow.asStateFlow()


    init {
        viewModelScope.launch {
            recipeRepository.getInfo(id).onSuccess {
             _infoFlow.value = Lce.Content(it)
         }
             .onFailure {
                 _infoFlow.value = Lce.Error(it)
             }
        }
    }

    private val _shopFlow = MutableSharedFlow<Lce<List<ExtendedIngredient>>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val shopFlow: Flow<Lce<List<ExtendedIngredient>>> = _shopFlow.asSharedFlow()

    fun onButtonClicked(){
        viewModelScope.launch {
            recipeRepository.getUserById(prefsManager.userId).onSuccess {
                val list = it.shopList.toMutableList()
                recipeRepository.getInfo(id).onSuccess {infoRecipe->
                    list.addAll(infoRecipe.extendedIngredients)
                    recipeRepository.updateShopList(list, prefsManager.userId)
                }.onFailure {
                    list.addAll(emptyList())
                }
                _shopFlow.tryEmit(Lce.Content(it.shopList))
            }.onFailure {
                _shopFlow.tryEmit(Lce.Error(it))
            }

        }
    }
}
