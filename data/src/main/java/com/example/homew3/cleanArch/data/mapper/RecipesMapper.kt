package com.example.homew3.cleanArch.data.mapper

import com.example.homew3.cleanArch.data.model.RecipesDTO
import com.example.homew3.cleanArch.data.model.RecipesEntity
import com.example.homew3.domain.model.Recipe

internal fun List<RecipesDTO>.toDomainModel(): List<Recipe> = map { it.toDomain() }

internal fun RecipesDTO.toDomain(): Recipe {
    return Recipe(
        id = id,
        title = title,
        image = image,
        missedIngredients = missedIngredients
    )
}

internal fun Recipe.toEntity():RecipesEntity {
    return RecipesEntity(
        id = id,
        title = title,
        image = image,
        missedIngredients = missedIngredients
    )
}

internal fun RecipesEntity.toDomains(): Recipe{
    return Recipe(
        id = id,
        title = title,
        image = image,
        missedIngredients = missedIngredients
    )
}
