package com.example.homew3.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissedIngredients(
    val originalName: String,
    val image: String,
    val original: String

) : Parcelable
