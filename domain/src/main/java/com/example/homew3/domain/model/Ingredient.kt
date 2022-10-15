package com.example.homew3.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val id: Long,
    val name: String,
    val localizedName: String,
    val image: String
) : Parcelable
