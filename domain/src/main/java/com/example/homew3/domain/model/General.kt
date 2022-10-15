package com.example.homew3.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class General(val steps: List<Step>) : Parcelable
