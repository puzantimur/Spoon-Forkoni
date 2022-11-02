package com.example.homew3.cleanArch.data.mapper

import com.example.homew3.cleanArch.data.model.dto.ExtendedIngredientDTO
import com.example.homew3.domain.model.ExtendedIngredient

internal fun List<ExtendedIngredientDTO>.toDomainListExtendedIngredient(): List<ExtendedIngredient> = map { it.toDomain() }

internal fun ExtendedIngredientDTO.toDomain(): ExtendedIngredient {
    return ExtendedIngredient(
        consistency = consistency,
        nameClean = nameClean,
        original = original,
        amount = amount,
        unit = unit,
        image = "https://spoonacular.com/cdn/ingredients_100x100/${image}"

    )
}