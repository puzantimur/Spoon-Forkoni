package com.example.homew3.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Equipment(
    val id: Long,
    val name: String,
    val localizedName: String,
    val image: String
) : Parcelable {
    override fun toString(): String {
        return localizedName
    }
}
