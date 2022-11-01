package com.example.homew3.domain.model

import java.util.*

data class User(
    val id: String,
    val login: String,
    val password: String,
    val email: String,
    val shopList: List<ExtendedIngredient>,
    val favorite: List<Recipe>
)