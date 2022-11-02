package com.example.homew3.cleanArch.data.mapper

import com.example.homew3.cleanArch.data.model.dto.StepDTO
import com.example.homew3.domain.model.Step

internal fun List<StepDTO>.toDomainListStep(): List<Step> = map { it.toDomain() }

internal fun StepDTO.toDomain(): Step {
    return Step(
        number = number,
        step = step,
        ingredients = ingredients.toDomainListIngredient(),
        equipment = equipment.toDomainListEquipment()
    )
}