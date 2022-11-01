package com.example.homew3.cleanArch.data.mapper

import com.example.homew3.cleanArch.data.model.dto.EquipmentDTO
import com.example.homew3.domain.model.Equipment


internal fun List<EquipmentDTO>.toDomainListEquipment(): List<Equipment> = map { it.toDomain() }

internal fun EquipmentDTO.toDomain(): Equipment {
    return Equipment(
        id = id,
        name = name,
        localizedName = localizedName,
        image = "https://spoonacular.com/cdn/equipment_100x100/${image}"

    )
}