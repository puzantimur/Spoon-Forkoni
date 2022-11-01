package com.example.homew3.domain.model

data class InfoRecipe(
  val vegetarian: Boolean,
  val cheap: Boolean,
  val aggregateLikes: Int,
  val pricePerServing: Double,
  val title: String,
  val spoonacularSourceUrl: String,
  val extendedIngredients: List<ExtendedIngredient>
)
