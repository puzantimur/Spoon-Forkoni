package com.example.homew3.cleanArch.data.mapper

import com.example.homew3.cleanArch.data.model.dto.RecipesDTO
import com.example.homew3.cleanArch.data.model.entity.RecipesEntity
import com.example.homew3.domain.model.Recipe

internal fun List<RecipesDTO>.toDomainModel(): List<Recipe> = map { it.toDomain() }

internal fun RecipesDTO.toDomain(): Recipe {
    return Recipe(
        id = id,
        title = title,
        image = image,
        missedIngredients = missedIngredients,
        likes = likes
    )
}

internal fun List<Recipe>.toListRecipeEntity(): List<RecipesEntity> = map { it.toEntity() }

internal fun Recipe.toEntity(): RecipesEntity {
    return RecipesEntity(
        id = id,
        title = title,
        image = image,
        missedIngredients = missedIngredients,
        likes = likes
    )
}

internal fun List<RecipesEntity>.toListDomain(): List<Recipe> = map { it.toDomain() }

internal fun RecipesEntity.toDomain(): Recipe {
    return Recipe(
        id = id,
        title = title,
        image = image,
        missedIngredients = missedIngredients,
        likes = likes
    )
}




