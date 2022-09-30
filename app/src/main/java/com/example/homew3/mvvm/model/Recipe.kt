package com.example.homew3.mvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String
)
