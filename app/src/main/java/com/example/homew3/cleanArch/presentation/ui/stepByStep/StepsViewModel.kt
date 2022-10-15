package com.example.homew3.cleanArch.presentation.ui.stepByStep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homew3.domain.model.Step
import com.example.homew3.domain.repository.ApiRecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StepsViewModel(
    private val apiRecipeRepository: ApiRecipeRepository,
    private val id: String
) : ViewModel() {

    private val _stepFlow = MutableStateFlow<List<Step>>(emptyList())

    val stepFlow: Flow<List<Step>> = _stepFlow.asStateFlow()

    init {
        viewModelScope.launch {
            apiRecipeRepository.getSteps(id)
                .onSuccess {
                    _stepFlow.value = it[0].steps
                }
                .onFailure {
                    _stepFlow.value = emptyList()
                }
        }
    }
}