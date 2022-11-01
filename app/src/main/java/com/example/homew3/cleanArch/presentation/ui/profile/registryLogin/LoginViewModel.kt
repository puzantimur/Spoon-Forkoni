package com.example.homew3.cleanArch.presentation.ui.profile.registryLogin

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.cleanArch.presentation.Lce
import com.example.homew3.cleanArch.presentation.utils.SharedPreferencesManager
import com.example.homew3.domain.model.User
import com.example.homew3.domain.repository.RecipeRepository
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val recipeRepository: RecipeRepository,
    context: Context
) : ViewModel() {

    private val prefsManager by lazy {
        SharedPreferencesManager(context)
    }


    private val _loginFlow = MutableSharedFlow<Lce<User>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val loginFlow: Flow<Lce<User>> = _loginFlow.asSharedFlow()

    fun onButtonClicked(login: String, password: String) {
        viewModelScope.launch {
            recipeRepository.loginUser(login, password).onSuccess {
                prefsManager.userId = it.id
                prefsManager.isLogined = true
                _loginFlow.tryEmit(Lce.Content(it))
            }.onFailure {
                _loginFlow.tryEmit(Lce.Error(it))
            }

        }
    }

}