package com.example.homew3.cleanArch.data.model.dto

import com.example.homew3.domain.model.MissedIngredients

internal data class RecipesDTO(
    val id: Int,
    val title: String,
    val image: String,
    val missedIngredients: List<MissedIngredients>,
    val likes: Int
)
