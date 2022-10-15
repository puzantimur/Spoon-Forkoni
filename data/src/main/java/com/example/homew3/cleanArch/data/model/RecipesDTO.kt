package com.example.homew3.cleanArch.data.model

import com.example.homew3.domain.model.MissedIngredients

internal data class RecipesDTO(
    val id: Int,
    val title: String,
    val image: String,
    val missedIngredients: Array<MissedIngredients>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RecipesDTO

        if (!missedIngredients.contentEquals(other.missedIngredients)) return false

        return true
    }

    override fun hashCode(): Int {
        return missedIngredients.contentHashCode()
    }
}
