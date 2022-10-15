package com.example.homew3.cleanArch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homew3.domain.model.MissedIngredients
import com.example.homew3.domain.model.Recipe

@Entity
data class RecipesEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val missedIngredients: Array<MissedIngredients>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Recipe

        if (!missedIngredients.contentEquals(other.missedIngredients)) return false

        return true
    }

    override fun hashCode(): Int {
        return missedIngredients.contentHashCode()
    }
}
