package com.example.homew3.cleanArch.presentation.ui.stepByStep

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.homew3.R
import com.example.homew3.cleanArch.presentation.ui.stepByStep.equipment.EquipmentAdapter
import com.example.homew3.cleanArch.presentation.ui.stepByStep.ingredients.IngredientsAdapter
import com.example.homew3.cleanArch.presentation.utils.ViewUtils.addItemDecorationLeftRight
import com.example.homew3.databinding.StepBinding
import com.example.homew3.domain.model.Step

class StepsAdapter(
    private val context: Context
) : ListAdapter<Step, StepsViewHolder>(DiffUtil) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsViewHolder {
        return StepsViewHolder(
            binding = StepBinding.inflate(layoutInflater, parent, false),
            context
        )
    }

    override fun onBindViewHolder(holder: StepsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        private val DiffUtil = object : DiffUtil.ItemCallback<Step>() {
            override fun areItemsTheSame(
                oldItem: Step,
                newItem: Step
            ): Boolean {
                return oldItem.number == newItem.number
            }

            override fun areContentsTheSame(
                oldItem: Step,
                newItem: Step
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class StepsViewHolder(
    private val binding: StepBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    private val outRectLeftRight = 60


    fun bind(item: Step) {

        with(binding) {
            if (item.equipment.isEmpty()) {
                equipment.isVisible = false
                recyclerViewEquipment.isVisible = false
            }
            if (item.ingredients.isEmpty()) {
                recyclerViewIngredients.isVisible = false
                ingredients.isVisible = false
            }
            step.text = item.number.toString()
            val adapterIngredients = IngredientsAdapter(context)
            recyclerViewIngredients.addItemDecorationLeftRight(outRectLeftRight, outRectLeftRight)
            recyclerViewIngredients.adapter = adapterIngredients
            adapterIngredients.submitList(item.ingredients)
            val adapterEquipment = EquipmentAdapter(context)
            recyclerViewEquipment.addItemDecorationLeftRight(outRectLeftRight, outRectLeftRight)
            recyclerViewEquipment.adapter = adapterEquipment
            adapterEquipment.submitList(item.equipment)
            descriptionList.text = item.step
        }
    }
}

