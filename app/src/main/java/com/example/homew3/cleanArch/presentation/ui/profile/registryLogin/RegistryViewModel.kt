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
import java.util.*

class RegistryViewModel(
    private val recipeRepository: RecipeRepository,
    context: Context
) : ViewModel() {


    private val _registryFlow = MutableSharedFlow<Lce<User>>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val prefsManager by lazy {
        SharedPreferencesManager(context)
    }

    val registryFlow: Flow<Lce<User>> = _registryFlow.asSharedFlow()


    fun onButtonClicked(login: String, password: String, email: String) {
        viewModelScope.launch {
            if (recipeRepository.validateUser(login, password).isFailure) {
                _registryFlow.tryEmit(
                    Lce.Content(
                        recipeRepository.registryUser(
                            User(
                                UUID.randomUUID().toString().also {
                                    prefsManager.userId = it
                                    prefsManager.isLogined = true
                                },
                                login,
                                password,
                                email,
                                emptyList(),
                                emptyList()
                            )
                        )
                    )
                )
            } else {
                _registryFlow.tryEmit(Lce.Error(Exception()))
            }
        }

    }


}