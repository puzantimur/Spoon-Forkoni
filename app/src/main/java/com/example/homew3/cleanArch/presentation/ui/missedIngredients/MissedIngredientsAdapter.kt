package com.example.homew3.cleanArch.presentation.ui.missedIngredients

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homew3.databinding.MissedIngredientsBinding
import com.example.homew3.domain.model.MissedIngredients

class MissedIngredientsAdapter(
    context: Context
) : ListAdapter<MissedIngredients, MissedIngredientsViewHolder>(DiffUtil) {


    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissedIngredientsViewHolder {
        return MissedIngredientsViewHolder(
            binding = MissedIngredientsBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MissedIngredientsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {

        private val DiffUtil = object : DiffUtil.ItemCallback<MissedIngredients>() {
            override fun areItemsTheSame(
                oldItem: MissedIngredients,
                newItem: MissedIngredients
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: MissedIngredients,
                newItem: MissedIngredients
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

}

class MissedIngredientsViewHolder(
    private val binding: MissedIngredientsBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MissedIngredients) {
        with(binding) {
            ingredientView.load(item.image)
            ingredientName.text = item.originalName
            ingredientAmount.text = item.original
        }
    }
}
