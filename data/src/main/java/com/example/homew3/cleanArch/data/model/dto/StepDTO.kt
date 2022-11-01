package com.example.homew3.cleanArch.data.model.dto

data class StepDTO(
    val number: Int,
    val step: String,
    val ingredients: List<IngredientDTO>,
    val equipment: List<EquipmentDTO>
)