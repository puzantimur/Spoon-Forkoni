package com.example.homew3.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Step(
    val number: Int,
    val step: String,
    val ingredients: List<Ingredient>,
    val equipment: List<Equipment>
) : Parcelable
