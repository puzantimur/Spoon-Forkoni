package com.example.homew3.cleanArch.data.mapper

import com.example.homew3.cleanArch.data.model.dto.InfoRecipesDTO
import com.example.homew3.domain.model.InfoRecipe

internal fun InfoRecipesDTO.toDomain(): InfoRecipe {
    return InfoRecipe(
        vegetarian,
        cheap,
        aggregateLikes,
        pricePerServing,
        title,
        spoonacularSourceUrl,
        extendedIngredients.toDomainListExtendedIngredient()
    )
}
