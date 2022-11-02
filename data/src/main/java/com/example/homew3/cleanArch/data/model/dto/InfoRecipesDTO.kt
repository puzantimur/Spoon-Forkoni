package com.example.homew3.cleanArch.data.model.dto

data class InfoRecipesDTO(
    val vegetarian: Boolean,
    val cheap: Boolean,
    val aggregateLikes: Int,
    val pricePerServing: Double,
    val title: String,
    val spoonacularSourceUrl: String,
    val extendedIngredients: List<ExtendedIngredientDTO>
)