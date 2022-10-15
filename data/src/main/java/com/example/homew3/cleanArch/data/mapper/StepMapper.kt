package com.example.homew3.cleanArch.data.mapper

import com.example.homew3.cleanArch.data.model.GeneralDTO
import com.example.homew3.domain.model.General

internal fun List<GeneralDTO>.toDomainModel(): List<General> = map { it.toDomain() }

internal fun GeneralDTO.toDomain(): General {
    return General(
        steps = steps
    )
}
