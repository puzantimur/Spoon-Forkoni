package com.example.homew3.cleanArch.data.mapper

import com.example.homew3.cleanArch.data.model.dto.IngredientDTO
import com.example.homew3.domain.model.Ingredient

internal fun List<IngredientDTO>.toDomainListIngredient(): List<Ingredient> = map { it.toDomain() }

internal fun IngredientDTO.toDomain(): Ingredient {
    return Ingredient(
        id = id,
        name = name,
        localizedName = localizedName,
        image = "https://spoonacular.com/cdn/ingredients_100x100/${image}"

    )
}