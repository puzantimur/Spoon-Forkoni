package com.example.homew3.domain.model

import java.io.Serializable


data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val missedIngredients: List<MissedIngredients>,
    val likes: Int
):Serializable
