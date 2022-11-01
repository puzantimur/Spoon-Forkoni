package com.example.homew3.cleanArch.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.homew3.domain.model.ExtendedIngredient
import com.example.homew3.domain.model.InfoRecipe
import java.util.*

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val login: String,
    val password: String,
    val email: String,
    val shopList: List<ExtendedIngredient>,
    val favorite: List<RecipesEntity>
)