package com.example.homew3.cleanArch.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homew3.domain.model.MissedIngredients

@Entity
data class RecipesEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String,
    val likes: Int,
    val missedIngredients: List<MissedIngredients>
)
