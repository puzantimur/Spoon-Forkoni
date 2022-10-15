package com.example.homew3.cleanArch.presentation.ui.equipment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homew3.databinding.EquipmentBinding
import com.example.homew3.domain.model.Equipment

class EquipmentAdapter(
    context: Context
) : ListAdapter<Equipment, EquipmentViewHolder>(DiffUtil) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        return EquipmentViewHolder(
            binding = EquipmentBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {

        val DiffUtil = object : DiffUtil.ItemCallback<Equipment>() {
            override fun areItemsTheSame(
                oldItem: Equipment,
                newItem: Equipment
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Equipment,
                newItem: Equipment
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}

class EquipmentViewHolder(
    private val binding: EquipmentBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Equipment) {
        with(binding) {
            equipmentView.load("https://spoonacular.com/cdn/equipment_100x100/${item.image}")
            equipmentName.text = item.localizedName
        }
    }
}