package com.example.homew3.cleanArch.presentation.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.domain.model.User
import com.example.homew3.domain.repository.RecipeRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val recipeRepository: RecipeRepository,
    private val id: String
) : ViewModel() {



    private val _userFlow = MutableStateFlow<Lce<User>>(Lce.Loading)

    val loginFlow: Flow<Lce<User>> = _userFlow.asStateFlow()

    init {
        viewModelScope.launch {
            recipeRepository.getUserById(id).onSuccess {
                _userFlow.value = Lce.Content(it)
            }.onFailure {
                _userFlow.value = Lce.Error(it)

            }
        }
    }

    fun onButtonClicked(id: String) {
        viewModelScope.launch {
            recipeRepository.getUserById(id).onSuccess {
                recipeRepository.deleteUser(it)
            }
        }
    }

}

